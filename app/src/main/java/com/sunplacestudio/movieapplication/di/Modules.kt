package com.sunplacestudio.movieapplication.di

import com.sunplacestudio.movieapplication.database.DataBase
import com.sunplacestudio.movieapplication.database.repository.MovieRepository
import com.sunplacestudio.movieapplication.database.repository.MovieRepositoryImpl
import com.sunplacestudio.movieapplication.database.room.MovieDao
import com.sunplacestudio.movieapplication.fragment.viewmodel.MovieFragmentViewModel
import com.sunplacestudio.movieapplication.fragment.viewmodel.MovieFragmentViewModelImpl
import com.sunplacestudio.movieapplication.utils.ApiKeyHelper
import org.koin.dsl.module
import kotlin.math.sin

val movieViewModelModule = module {
    factory { MovieFragmentViewModelImpl(get()) }
}

val dataBaseModule = module {
    single { DataBase.buildDataBase(get()) }

    fun provideVersions(dbMain: DataBase): MovieDao {
        return dbMain.getMovieDao()
    }

    single { provideVersions(get()) }

    single{ MovieRepositoryImpl(get()) }
}

val apiKeyModule = module {
    single { ApiKeyHelper() }
}