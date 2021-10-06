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
import com.marvelapp.ui.comic.paging.ComicDataSource
import com.marvelapp.ui.comic.paging.ComicDataSourceFactory

class MarvelViewModal: ViewModel()  {

    private val comicPagedListConfig: PagedList.Config
    private val comicItemDataSourceFactory: ComicDataSourceFactory
    var comicItemPagedList: LiveData<PagedList<PagingItem?>>

    var comicError = MutableLiveData<Errors>()
    var comicResponse = MutableLiveData<PagingListResponse>()
    var comicBottomLoader = MutableLiveData<Boolean>()

    private val characterPagedListConfig: PagedList.Config
    private val characterItemDataSourceFactory: CharacterDataSourceFactory
    var characterItemPagedList: LiveData<PagedList<PagingItem?>>

    var characterError = MutableLiveData<Errors>()
    var charResponse = MutableLiveData<PagingListResponse>()
    var characterBottomLoader = MutableLiveData<Boolean>()

    fun updateComicValues( filter: String?) {
        comicItemDataSourceFactory.filter = filter
    }

    fun updateCharacterValues( search: String?) {
        characterItemDataSourceFactory.search = search
    }

    init {

        //character data
        characterItemDataSourceFactory =
            CharacterDataSourceFactory(characterError, charResponse, characterBottomLoader)
        characterPagedListConfig = PagedList.Config.Builder()
            .setPageSize(CharacterDataSource.PAGE_SIZE)
            .setInitialLoadSizeHint(CharacterDataSource.PAGE_SIZE)
            .setPrefetchDistance(6)
            .setEnablePlaceholders(true)
            .build()
        characterItemPagedList = LivePagedListBuilder(
            characterItemDataSourceFactory,
            characterPagedListConfig
        ).build()

        //comic data
        comicItemDataSourceFactory =
            ComicDataSourceFactory(comicError, comicResponse, comicBottomLoader)
        comicPagedListConfig = PagedList.Config.Builder()
            .setPageSize(ComicDataSource.PAGE_SIZE)
            .setInitialLoadSizeHint(ComicDataSource.PAGE_SIZE)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(6)
            .build()
        comicItemPagedList = LivePagedListBuilder(
            comicItemDataSourceFactory,
            comicPagedListConfig
        ).build()
    }

}