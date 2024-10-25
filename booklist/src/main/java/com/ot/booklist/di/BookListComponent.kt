package com.ot.booklist.di

import com.ot.booklist.BookListFragment
import dagger.Subcomponent

@Subcomponent(modules = [BookListModule::class])
interface BookListComponent {
    fun inject(fragment: BookListFragment)
}