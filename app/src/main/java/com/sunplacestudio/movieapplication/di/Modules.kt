package com.sunplacestudio.movieapplication.di

import com.sunplacestudio.movieapplication.database.DataBase
import com.sunplacestudio.movieapplication.database.repository.MovieRepositoryImpl
import com.sunplacestudio.movieapplication.database.room.MovieDao
import com.sunplacestudio.movieapplication.fragment.viewmodel.MovieFragmentViewModelImpl
import com.sunplacestudio.movieapplication.utils.ApiHelper
import org.koin.dsl.module

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
    single { ApiHelper() }
}