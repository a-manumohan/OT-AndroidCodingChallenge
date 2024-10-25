package com.ot.booklist.di

import com.ot.booklist.api.BookListApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
interface BookListModule {
    companion object {
        @Provides
        fun retrofit(): Retrofit = Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create()
            ).build()

        @Provides
        fun bookListApi(retrofit: Retrofit): BookListApi = retrofit.create()
    }
}