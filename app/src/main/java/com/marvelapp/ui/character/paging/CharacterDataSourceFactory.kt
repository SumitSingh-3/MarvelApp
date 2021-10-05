package com.marvelapp.ui.character.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.character.Character
import com.marvelapp.modals.character.CharacterResponse

class CharacterDataSourceFactory(
    var error: MutableLiveData<Errors>,
    var charResponse: MutableLiveData<CharacterResponse>,
    var bottomLoader: MutableLiveData<Boolean>
) :
    DataSource.Factory<Int, Character>() {

    var itemLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, Character>> = MutableLiveData()
    private var itemDataSource: CharacterDataSource? = null
    var search: String? = null

    override fun create(): DataSource<Int, Character> {
        itemDataSource = CharacterDataSource(error, charResponse, bottomLoader, search)
        itemLiveDataSource.postValue(itemDataSource!!)
        return itemDataSource as CharacterDataSource
    }
}