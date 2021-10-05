package com.marvelapp.ui.comic.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.marvelapp.domain.APICallHandler
import com.marvelapp.domain.APICallManager
import com.marvelapp.domain.APIType
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.base.PagingListResponse
import com.marvelapp.modals.common.PagingItem

class ComicDataSource (
    var error: MutableLiveData<Errors>,
    var response: MutableLiveData<PagingListResponse>,
    var bottomLoader: MutableLiveData<Boolean>,
    var filter: String?
): PageKeyedDataSource<Int, PagingItem>(), APICallHandler<Any?> {

    private var callbackFirst: LoadInitialCallback<Int, PagingItem>? = null
    private var callbackAfter: LoadCallback<Int, PagingItem>? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PagingItem>
    ) {
        callbackFirst = callback
        val apiCallManager = APICallManager(APIType.COMIC, this)
        apiCallManager.getComicAPI(FIRST_PAGE, PAGE_SIZE, filter)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PagingItem>) {
    }

    var params1: LoadParams<Int>? = null
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PagingItem>) {
        params1 = params
        callbackAfter = callback
        bottomLoader.postValue(true)
        val apiCallManager = APICallManager(APIType.COMIC_AFTER, this)
        apiCallManager.getComicAPI(params.key + PAGE_SIZE, PAGE_SIZE, filter)
    }

    override fun onSuccess(apiType: APIType, response: Any?) {
        when(apiType) {
            APIType.COMIC -> {
                val charRes: PagingListResponse? = response as PagingListResponse?
                if (callbackFirst != null && charRes?.results != null) {
                    val data: MutableList<PagingItem> = ArrayList()
                    this.response.postValue(charRes)
                    data.addAll(charRes.results)
                    callbackFirst!!.onResult(data, null, FIRST_PAGE)
                }
            }
            APIType.COMIC_AFTER -> {
                val charRes = response as PagingListResponse?
                bottomLoader.postValue(false)
                if (callbackAfter != null && charRes?.results != null) {
                    callbackAfter!!.onResult(charRes.results, params1!!.key + PAGE_SIZE)
                }
            }
        }
    }

    override fun onFailure(apiType: APIType, error: Any?) {
        val er = Errors()
        er.message = "Api Error $error"
        this.error.postValue(er)
    }

    companion object {
        const val PAGE_SIZE = 30
        private const val FIRST_PAGE = 0
    }
}