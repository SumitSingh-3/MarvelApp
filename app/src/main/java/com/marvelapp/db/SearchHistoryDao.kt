package com.marvelapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: SearchHistory)

    @Query("SELECT * FROM SEARCH_HISTORY_TABLE")
    fun getAllSearchData(): LiveData<List<SearchHistory>>
}