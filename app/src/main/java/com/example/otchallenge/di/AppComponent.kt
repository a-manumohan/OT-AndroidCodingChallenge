package com.example.otchallenge.di

import androidx.fragment.app.FragmentFactory
import com.example.otchallenge.MainActivity
import com.ot.booklist.di.BookListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FragmentModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun bookListComponent(): BookListComponent

    fun fragmentFactory(): FragmentFactory
}
