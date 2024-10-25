package com.ot.booklist.di

import androidx.fragment.app.Fragment
import com.ot.booklist.BookListFragment
import com.ot.core.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BookListFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(BookListFragment::class)
    fun bookListFragment(fragment: BookListFragment): Fragment
}