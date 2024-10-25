package com.example.otchallenge.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class AppFragmentFactory
    @Inject
    constructor(
        private val fragmentProviders: @JvmSuppressWildcards Map<Class<out Fragment>, Provider<Fragment>>,
    ) : FragmentFactory() {
        override fun instantiate(
            classLoader: ClassLoader,
            className: String,
        ): Fragment {
            val fragmentClass = loadFragmentClass(classLoader, className)
            val provider = fragmentProviders[fragmentClass]
            return provider?.get() ?: super.instantiate(classLoader, className)
        }
    }
