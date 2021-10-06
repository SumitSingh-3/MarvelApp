package com.marvelapp.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelLazy
import androidx.recyclerview.widget.GridLayoutManager
import com.marvelapp.databinding.FragmentCharacterBinding
import com.marvelapp.db.SearchHistory
import com.marvelapp.db.SearchHistoryRepository
import com.marvelapp.modals.base.PagingListResponse
import com.marvelapp.ui.character.paging.CharacterPagingAdapter
import com.marvelapp.utils.AppUtil
import com.marvelapp.viewmodal.MarvelViewModal


class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var pagingAdapter: CharacterPagingAdapter

    private val viewModel: MarvelViewModal by ViewModelLazy(
        MarvelViewModal::class,
        { requireActivity().viewModelStore },
        { defaultViewModelProviderFactory }
    )

    private lateinit var charRes: PagingListResponse

    private val searchHistoryRepository = SearchHistoryRepository()

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


        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchCharacter()
                    true
                }
                else -> false
            }
        }

        binding.clearText.setOnClickListener{
            binding.searchBar.setText("")
            binding.clearText.visibility = View.GONE
            searchCharacter()
        }

    }

    private fun searchCharacter(){
        binding.searchBar.clearFocus()
        val searchText = binding.searchBar.text.toString()
        binding.loader.visibility = View.VISIBLE
        AppUtil.hideKeyboard(binding.searchBar)

        if (searchText.isNotEmpty()) {
            insetInDb(searchText)
            binding.clearText.visibility = View.VISIBLE
        }

        viewModel.updateCharacterValues(if (searchText.isEmpty()) null else searchText)
        viewModel.characterItemPagedList.value?.dataSource?.invalidate()
    }

    private fun insetInDb(search:String){
        val searchHistory= SearchHistory(search)
        searchHistoryRepository.insert(searchHistory)
    }


    private fun setObserver() {

        searchHistoryRepository.searchHistory.observe(viewLifecycleOwner){it->
            println("db data ----- $it")
            if(it.isNotEmpty()) {

                val countries: Array<String?> = AppUtil.getSearchHistoryArray(it)
                // Create the adapter and set it to the AutoCompleteTextView
                activity?.let {
                    ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, countries).also { adapter ->
                        binding.searchBar.setAdapter(adapter)
                    }
                }
            }
        }

        viewModel.charResponse.observe(viewLifecycleOwner){
            charRes = it
            binding.loader.visibility = View.GONE
        }

        viewModel.characterItemPagedList.observe(viewLifecycleOwner)
        {
            pagingAdapter.submitList(it)
        }

        viewModel.characterBottomLoader.observe(viewLifecycleOwner) {
            binding.bottomLoader.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.characterError.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
        }
    }

}