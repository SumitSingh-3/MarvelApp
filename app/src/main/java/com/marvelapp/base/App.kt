package com.marvelapp.base

import android.app.Application

class App : Application() {

    companion object {
        var INSTANCE: App = App()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this@App
    }

}