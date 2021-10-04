package com.marvelapp.domain

import com.marvelapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class APIInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
       val req = chain.request()
        val url = req.url.newBuilder().addEncodedQueryParameter("ts", Constants.ts)
            .addEncodedQueryParameter("apikey", Constants.API_KEY)
            .addEncodedQueryParameter("hash", Constants.hash())
            .build()

        val request = req.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}