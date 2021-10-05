package com.marvelapp.domain;

import com.marvelapp.modals.base.BaseResponse
import com.marvelapp.modals.base.PagingListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {


    @GET("v1/public/characters")
    fun getCharacterList(
        @Query("offset") pageNo: Int,
        @Query("limit") limit: Int,
        @Query("nameStartsWith") search: String?
    ): Call<BaseResponse<PagingListResponse?>>
}