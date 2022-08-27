package com.sunplacestudio.movieapplication.database.repository

import com.sunplacestudio.movieapplication.database.room.MovieDao
import com.sunplacestudio.movieapplication.database.room.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MovieRepository(
    private val movieDao: MovieDao
) {

    private val ioScheduler = Schedulers.io()

    fun getMoviesLiveData(): Flowable<List<MovieCategoryList>> {
        return movieDao.getMoviesLiveData().subscribeOn(ioScheduler).flatMap {
            val array: ArrayList<MovieCategoryList> = ArrayList()
            for (movieEntity in it) {
                array.add(convertToMovie(movieEntity))
            }
            return@flatMap Flowable.fromArray(array)
        }
    }

    private fun convertToMovie(movieEntity: MovieEntity): MovieCategoryList {
        return MovieCategoryList(movieEntity.nameCategory, movieEntity.listMovies)
    }

    fun getMovies(): Single<List<MovieCategoryList>> {
        return Single.fromCallable {
            movieDao.getMovies().map { convertToMovie(it) }
        }.subscribeOn(ioScheduler)
    }

    fun addMovies(movie: MovieCategoryList): Completable {
        return Completable.fromCallable {
            movieDao.addMovies(MovieEntity(movie.category, movie.list))
        }.subscribeOn(ioScheduler)
    }

    fun clear(): Completable {
        return Completable.fromCallable {
            movieDao.clear()
        }.subscribeOn(ioScheduler)
    }

}