package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.DaggerAppComponent
import com.ot.booklist.di.BookListComponent
import com.ot.booklist.di.BookListComponentProvider

class MyApplication : Application(), BookListComponentProvider {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

    override fun bookListComponent() = appComponent.bookListComponent()
}
