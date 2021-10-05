package com.marvelapp.domain

import com.marvelapp.modals.base.BaseResponse
import com.marvelapp.modals.base.Errors
import com.marvelapp.modals.base.PagingListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class APICallManager<T>(
    private val mCallType: APIType,
    private val mAPICallHandler: APICallHandler<T>,
) : Callback<BaseResponse<T>> {

    override fun onResponse(call: Call<BaseResponse<T>>?, response: Response<BaseResponse<T>>) {
        if (response.code() == APIStatusCode.OK) {
            val body = response.body()
            if (body != null) {
                if (body.code == APIStatusCode.OK) {
                    mAPICallHandler.onSuccess(mCallType, body.data)
                } else {
                    mAPICallHandler.onFailure(mCallType, body.data)
                }
            }
        } else {
            val errors = Errors()
            errors.message = "Something Went Wrong"
            mAPICallHandler.onFailure(mCallType, errors)
        }
    }

    override fun onFailure(call: Call<BaseResponse<T>>?, throwable: Throwable) {
        val message: String? =
            if (throwable is UnknownHostException || throwable is SocketException || throwable is SocketTimeoutException) {
                "Network Connection Problem"
            } else {
                throwable.message
            }
        val errors = Errors()
        errors.message = message
        mAPICallHandler.onFailure(mCallType, errors)
    }

    fun getCharactersAPI(
        pageNo: Int,
        limit: Int,
        search: String?,
    ) {
        APIClient.getClient().getCharacterList(pageNo, limit, search)
            .enqueue(this@APICallManager as Callback<BaseResponse<PagingListResponse?>>)
    }

    fun getComicAPI(
        pageNo: Int,
        limit: Int,
        filter: String?,
    ) {
        APIClient.getClient().getComicList(pageNo, limit, filter)
            .enqueue(this@APICallManager as Callback<BaseResponse<PagingListResponse?>>)
    }
}
