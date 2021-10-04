package com.marvelapp.modals.character

data class CharacterResponse(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Character>,
    val total: Int
)

data class Character(
    val description: String,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val thumbnail: Thumbnail,
)

data class Thumbnail(
    val extension: String,
    val path: String
)
