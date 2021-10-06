package com.marvelapp.db

import androidx.lifecycle.LiveData
import com.marvelapp.base.App

class SearchHistoryRepository {

    var searchHistoryDao: SearchHistoryDao = AppDatabase.getDatabase(App.INSTANCE).searchHistoryDao()!!

    val searchHistory : LiveData<List<SearchHistory>> = searchHistoryDao.getAllSearchData()

    fun insert(search: SearchHistory) {
        searchHistoryDao.insert(search)
    }


}