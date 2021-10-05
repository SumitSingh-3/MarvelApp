package com.marvelapp.ui.character.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.base.PagingListResponse
import com.marvelapp.modals.common.PagingItem

class CharacterDataSourceFactory(
    var error: MutableLiveData<Errors>,
    var charResponse: MutableLiveData<PagingListResponse>,
    var bottomLoader: MutableLiveData<Boolean>
) :
    DataSource.Factory<Int, PagingItem>() {

    var itemLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, PagingItem>> = MutableLiveData()
    private var itemDataSource: CharacterDataSource? = null
    var search: String? = null

    override fun create(): DataSource<Int, PagingItem> {
        itemDataSource = CharacterDataSource(error, charResponse, bottomLoader, search)
        itemLiveDataSource.postValue(itemDataSource!!)
        return itemDataSource as CharacterDataSource
    }
}