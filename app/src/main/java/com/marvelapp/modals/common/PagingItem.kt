package com.marvelapp.modals.common

data class PagingItem (
    val id: Int,
    val thumbnail: Thumbnail,
    val title: String,
    val name: String,
)

data class Thumbnail(
    val extension: String,
    val path: String
)