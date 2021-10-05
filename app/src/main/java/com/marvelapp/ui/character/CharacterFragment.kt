package com.marvelapp.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.marvelapp.databinding.FragmentCharacterBinding
import com.marvelapp.modals.character.CharacterResponse
import com.marvelapp.ui.character.paging.CharacterPagingAdapter
import com.marvelapp.utils.AppUtil
import com.marvelapp.viewmodal.CharacterViewModal


class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var pagingAdapter: CharacterPagingAdapter
    private val viewModel: CharacterViewModal by viewModels()
    private lateinit var charRes: CharacterResponse

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


        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchCharacter()
                    true
                }
                else -> false
            }
        }

        setObserver()
    }

    private fun searchCharacter(){
        val searchText = binding.searchBar.text.toString()
        binding.loader.visibility = View.VISIBLE
        AppUtil.hideKeyboard(binding.searchBar)
        viewModel.updateValues(if (searchText.isEmpty()) null else searchText)
        viewModel.itemPagedList.value?.dataSource?.invalidate()
    }


    private fun setObserver() {

        viewModel.charResponse.observe(viewLifecycleOwner){
            charRes = it
            binding.loader.visibility = View.GONE
        }

        viewModel.itemPagedList.observe(viewLifecycleOwner)
        {
            pagingAdapter.submitList(it)
        }

        viewModel.bottomLoader.observe(viewLifecycleOwner) {
            binding.bottomLoader.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
        }
    }

}