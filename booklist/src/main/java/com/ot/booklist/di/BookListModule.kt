package com.ot.booklist.di

import com.ot.booklist.BookListRepository
import com.ot.booklist.BookListRepositoryImpl
import com.ot.booklist.api.BookListApi
import com.ot.core.di.BaseUrl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
interface BookListModule {
    @Binds
    fun bookListRepository(repositoryImpl: BookListRepositoryImpl): BookListRepository

    companion object {
        @Provides
        fun retrofit(@BaseUrl baseUrl: String): Retrofit =
            Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                    MoshiConverterFactory.create(),
                ).build()

        @Provides
        fun bookListApi(retrofit: Retrofit): BookListApi = retrofit.create()
    }
}
