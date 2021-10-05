package com.marvelapp.ui.character.paging

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.marvelapp.domain.APICallHandler
import com.marvelapp.domain.APICallManager
import com.marvelapp.domain.APIType
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.character.Character
import com.marvelapp.modals.character.CharacterResponse

class CharacterDataSource(
    var error: MutableLiveData<Errors>,
    var charResponse: MutableLiveData<CharacterResponse>,
    var bottomLoader: MutableLiveData<Boolean>,
    var search: String?
    ): PageKeyedDataSource<Int, Character>(), APICallHandler<Any?> {

    private var callbackFirst: LoadInitialCallback<Int, Character>? = null
    private var callbackAfter: LoadCallback<Int, Character>? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        callbackFirst = callback
        val apiCallManager = APICallManager(APIType.CHARACTER, this)
        apiCallManager.getCharactersAPI(FIRST_PAGE, PAGE_SIZE, search)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
    }

    var params1: LoadParams<Int>? = null
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        params1 = params
        callbackAfter = callback
        bottomLoader.postValue(true)
        val apiCallManager = APICallManager(APIType.CHARACTER_AFTER, this)
        apiCallManager.getCharactersAPI(params.key + PAGE_SIZE, PAGE_SIZE, search)
    }

    override fun onSuccess(apiType: APIType, response: Any?) {
       when(apiType) {
           APIType.CHARACTER -> {
               val charRes: CharacterResponse? = response as CharacterResponse?
               if (callbackFirst != null && charRes?.results != null) {
                   val videoData: MutableList<Character> = ArrayList()
                   this.charResponse.postValue(charRes)
                   videoData.addAll(charRes.results)
                   callbackFirst!!.onResult(videoData, null, FIRST_PAGE)
               }
           }
           APIType.CHARACTER_AFTER -> {
               val charRes = response as CharacterResponse?
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