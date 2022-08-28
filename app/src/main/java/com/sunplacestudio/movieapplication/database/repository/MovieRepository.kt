package com.sunplacestudio.movieapplication.database.repository

import com.sunplacestudio.movieapplication.database.room.MovieDao
import com.sunplacestudio.movieapplication.database.room.MovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieRepository(
    private val movieDao: MovieDao
) {
    private val dispatcher = Dispatchers.IO
    private val scopeIO = CoroutineScope(dispatcher)

    suspend fun getMoviesLiveData(): List<MovieCategoryList> = withContext(dispatcher) {
        movieDao.getMoviesLiveData().map { it.toMovieCategoryList() }
    }

    private fun MovieEntity.toMovieCategoryList(): MovieCategoryList {
        return MovieCategoryList(nameCategory, listMovies)
    }

    suspend fun getMovies(): List<MovieCategoryList> = withContext(dispatcher) {
        movieDao.getMovies().map { it.toMovieCategoryList() }
    }

    fun addMovies(movie: MovieCategoryList) = scopeIO.launch {
        movieDao.addMovies(MovieEntity(movie.category, movie.list))
    }

    fun clear() = scopeIO.launch {
        movieDao.clear()
    }
}