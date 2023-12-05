package com.example.mybookshelfapp

import android.app.Application
import com.example.mybookshelfapp.data.AppContainer
import com.example.mybookshelfapp.data.DefaultAppContainer

class BookshelfApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}