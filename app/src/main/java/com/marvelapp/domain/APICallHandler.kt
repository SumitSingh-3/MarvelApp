package com.marvelapp.domain

interface APICallHandler<T> {

    fun onSuccess(apiType: APIType, response: T?)

    fun onFailure(apiType: APIType, error: Any?)
}