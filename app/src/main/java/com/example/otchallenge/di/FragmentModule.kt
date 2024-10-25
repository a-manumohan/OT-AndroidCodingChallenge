package com.example.otchallenge.di

import androidx.fragment.app.FragmentFactory
import com.ot.booklist.di.BookListFragmentModule
import dagger.Binds
import dagger.Module

@Module(includes = [BookListFragmentModule::class])
interface FragmentModule {
    @Binds
    fun fragmentFactory(factory: AppFragmentFactory): FragmentFactory
}