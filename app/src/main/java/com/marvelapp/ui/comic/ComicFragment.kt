package com.marvelapp.ui.comic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.marvelapp.R
import com.marvelapp.databinding.FragmentComicBinding
import com.marvelapp.modals.base.PagingListResponse
import com.marvelapp.ui.comic.paging.ComicAdapter
import com.marvelapp.utils.AppUtil
import com.marvelapp.viewmodal.CharacterViewModal
import com.marvelapp.viewmodal.ComicViewModal


class ComicFragment : Fragment() {

    private lateinit var binding: FragmentComicBinding
    private lateinit var adapter: ComicAdapter

    private val viewModel: ComicViewModal by viewModels()
    private lateinit var response: PagingListResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentComicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init
        binding.recyclerView.layoutManager  = GridLayoutManager(activity, 3)

        adapter = ComicAdapter()
        binding.recyclerView.adapter = adapter

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            var filter: String? = null
            when(chip?.id) {
                R.id.thisWeek -> {
                    filter = AppUtil.getThisWeek()
                }

                R.id.lastWeek -> {
                    filter = AppUtil.getLastWeek()
                }

                R.id.nextWeek -> {
                    filter = AppUtil.getNextWeek()
                }

                R.id.thisMonth ->{
                    filter = AppUtil.getThisMonth()
                }
            }

            applyFilter(filter)

            if (checkedId != -1) {
                Toast.makeText(context, "Apply ${chip?.text} Filter", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Remove Filter", Toast.LENGTH_SHORT).show()
            }


        }

        setObserver()
    }

    private fun applyFilter(filter: String?) {
        binding.loader.visibility = View.VISIBLE
        viewModel.updateValues(filter)
        viewModel.itemPagedList.value?.dataSource?.invalidate()
    }

    private fun setObserver() {

        viewModel.response.observe(viewLifecycleOwner){
            response = it
            binding.loader.visibility = View.GONE
        }

        viewModel.itemPagedList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }

        viewModel.bottomLoader.observe(viewLifecycleOwner) {
            binding.bottomLoader.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
        }
    }

}