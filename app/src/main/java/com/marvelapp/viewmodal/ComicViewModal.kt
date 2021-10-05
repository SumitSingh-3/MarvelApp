package com.marvelapp.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.base.PagingListResponse
import com.marvelapp.modals.common.PagingItem
import com.marvelapp.ui.comic.paging.ComicDataSource
import com.marvelapp.ui.comic.paging.ComicDataSourceFactory

class ComicViewModal: ViewModel() {

    private val pagedListConfig: PagedList.Config
    private val itemDataSourceFactory: ComicDataSourceFactory
    var itemPagedList: LiveData<PagedList<PagingItem?>>

    var error = MutableLiveData<Errors>()
    var response = MutableLiveData<PagingListResponse>()
    var bottomLoader = MutableLiveData<Boolean>()

    fun updateValues( filter: String?) {
        itemDataSourceFactory.filter = filter
    }

    init {
        itemDataSourceFactory =
            ComicDataSourceFactory(error, response, bottomLoader)
        pagedListConfig = PagedList.Config.Builder()
            .setPageSize(ComicDataSource.PAGE_SIZE)
            .setInitialLoadSizeHint(ComicDataSource.PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
        itemPagedList = LivePagedListBuilder(
            itemDataSourceFactory,
            pagedListConfig
        ).build()
    }

}