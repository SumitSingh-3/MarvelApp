package com.marvelapp.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.character.Character
import com.marvelapp.modals.character.CharacterResponse
import com.marvelapp.ui.character.paging.CharacterDataSource
import com.marvelapp.ui.character.paging.CharacterDataSourceFactory

class CharacterViewModal : ViewModel() {

    private val pagedListConfig: PagedList.Config
    private val itemDataSourceFactory: CharacterDataSourceFactory
    var itemPagedList: LiveData<PagedList<Character?>>

    var error = MutableLiveData<Errors>()
    var charResponse = MutableLiveData<CharacterResponse>()

    fun updateValues( search: String) {
        itemDataSourceFactory.search = search
    }

    init {
        itemDataSourceFactory =
            CharacterDataSourceFactory(error, charResponse)
        pagedListConfig = PagedList.Config.Builder()
            .setPageSize(CharacterDataSource.PAGE_SIZE)
            .setInitialLoadSizeHint(CharacterDataSource.PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
        itemPagedList = LivePagedListBuilder(
            itemDataSourceFactory,
            pagedListConfig
        ).build()
    }

}