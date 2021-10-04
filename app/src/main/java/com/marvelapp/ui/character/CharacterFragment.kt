package com.marvelapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.marvelapp.databinding.FragmentCharacterBinding
import com.marvelapp.ui.character.paging.CharacterPagingAdapter
import com.marvelapp.viewmodal.CharacterViewModal

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private val viewModel: CharacterViewModal by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 3)

        pagingAdapter = CharacterPagingAdapter()
        binding.recyclerView.adapter = pagingAdapter

        setObserver()
    }

    private fun setObserver() {
        viewModel.itemPagedList.observe(viewLifecycleOwner)
        {
            println("itemPagedList- fragment")
            pagingAdapter.submitList(it)
        }
    }

}