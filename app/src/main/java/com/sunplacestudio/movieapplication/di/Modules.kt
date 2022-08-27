package com.sunplacestudio.movieapplication.di

import com.sunplacestudio.movieapplication.database.DataBase
import com.sunplacestudio.movieapplication.database.repository.MovieRepository
import com.sunplacestudio.movieapplication.database.room.MovieDao
import com.sunplacestudio.movieapplication.fragment.main.viewmodel.MovieFragmentViewModel
import com.sunplacestudio.movieapplication.fragment.movie.MovieViewModel
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.NetworkUtils
import com.sunplacestudio.movieapplication.utils.apicall.MovieApiCall
import org.koin.dsl.module

val viewModelModule = module {
    factory { MovieFragmentViewModel(get(), get(), get(), get(), get()) }
    factory { MovieViewModel(get(), get()) }
}

val dataBaseModule = module {
    single { DataBase.buildDataBase(get()) }

    fun provideVersions(dbMain: DataBase): MovieDao {
        return dbMain.getMovieDao()
    }

    single { provideVersions(get()) }

    single { MovieRepository(get()) }
}

val networkModule = module {

    single { NetworkUtils(get()) }

    single { MovieApiCall(get(), get()) }

}

val apiKeyModule = module {
    single { ApiHelper() }
}

val modulesList = viewModelModule + dataBaseModule + networkModule + apiKeyModule