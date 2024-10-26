package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import com.ot.booklist.di.BookListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun bookListComponent(): BookListComponent
}
