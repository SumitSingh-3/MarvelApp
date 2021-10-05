package com.marvelapp.ui.comic.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.base.PagingListResponse
import com.marvelapp.modals.common.PagingItem

class ComicDataSourceFactory(
    var error: MutableLiveData<Errors>,
    var response: MutableLiveData<PagingListResponse>,
    var bottomLoader: MutableLiveData<Boolean>
) :
    DataSource.Factory<Int, PagingItem>() {

    var itemLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, PagingItem>> = MutableLiveData()
    private var itemDataSource: ComicDataSource? = null
    var filter: String? = null

    override fun create(): DataSource<Int, PagingItem> {
        itemDataSource = ComicDataSource(error, response, bottomLoader, filter)
        itemLiveDataSource.postValue(itemDataSource!!)
        return itemDataSource as ComicDataSource
    }
}