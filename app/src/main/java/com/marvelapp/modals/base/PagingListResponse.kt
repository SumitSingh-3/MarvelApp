package com.marvelapp.modals.base

import com.marvelapp.modals.common.PagingItem

data class PagingListResponse (
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<PagingItem>,
    val total: Int
)