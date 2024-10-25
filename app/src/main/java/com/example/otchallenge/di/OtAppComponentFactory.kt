package com.example.otchallenge.di

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.app.AppComponentFactory
import com.example.otchallenge.MainActivity
import com.example.otchallenge.MyApplication

class OtAppComponentFactory : AppComponentFactory() {

    private lateinit var application: MyApplication

    override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
        val application = super.instantiateApplicationCompat(cl, className)
        this.application = application as MyApplication
        return application
    }

    override fun instantiateActivityCompat(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): Activity {
        return MainActivity(application.appComponent.fragmentFactory())
    }
}