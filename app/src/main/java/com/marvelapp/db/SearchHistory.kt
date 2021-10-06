package com.marvelapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history_table")
data class SearchHistory(
    @PrimaryKey @ColumnInfo(name = "search") val search: String,
)