package com.marvelapp.modals.base

class BaseResponse<T> {
    var code = 0
    var status: String? = null
    var data: T? = null
}