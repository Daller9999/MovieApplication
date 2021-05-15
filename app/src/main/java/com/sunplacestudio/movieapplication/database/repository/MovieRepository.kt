package com.sunplacestudio.movieapplication.database.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface MovieRepository {

    fun getMoviesLiveData(): Flowable<List<MovieCategoryList>>

    fun getMovies(): Single<List<MovieCategoryList>>

    fun addMovies(movie: MovieCategoryList): Completable

    fun clear(): Completable
}