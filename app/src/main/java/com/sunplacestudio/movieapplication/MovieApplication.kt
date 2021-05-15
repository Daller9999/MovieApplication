package com.sunplacestudio.movieapplication

import android.app.Application
import com.sunplacestudio.movieapplication.di.apiKeyModule
import com.sunplacestudio.movieapplication.di.dataBaseModule
import com.sunplacestudio.movieapplication.di.movieViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(listOf(movieViewModelModule, dataBaseModule, apiKeyModule))
        }
    }
}