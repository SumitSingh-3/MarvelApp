package com.marvelapp.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.base.PagingListResponse
import com.marvelapp.modals.common.PagingItem
import com.marvelapp.ui.character.paging.CharacterDataSource
import com.marvelapp.ui.character.paging.CharacterDataSourceFactory

class CharacterViewModal : ViewModel() {

    private val pagedListConfig: PagedList.Config
    private val itemDataSourceFactory: CharacterDataSourceFactory
    var itemPagedList: LiveData<PagedList<PagingItem?>>

    var error = MutableLiveData<Errors>()
    var charResponse = MutableLiveData<PagingListResponse>()
    var bottomLoader = MutableLiveData<Boolean>()

    fun updateValues( search: String?) {
        itemDataSourceFactory.search = search
    }

    init {
        itemDataSourceFactory =
            CharacterDataSourceFactory(error, charResponse, bottomLoader)
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