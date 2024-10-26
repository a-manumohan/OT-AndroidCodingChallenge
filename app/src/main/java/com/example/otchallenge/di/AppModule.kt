package com.example.otchallenge.di

import com.example.otchallenge.BuildConfig
import com.ot.core.AppDispatchers
import com.ot.core.di.BaseUrl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
interface AppModule {
    companion object {
        @Provides
        @BaseUrl
        fun baseUrl(): String = BuildConfig.BASE_URL

        @Provides
        fun dispatchers() =
            AppDispatchers(
                work = Dispatchers.IO,
                result = Dispatchers.Main,
            )
    }
}
