package com.sunplacestudio.movieapplication.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Query("select * from MoviesTable")
    fun getMoviesLiveData(): Flowable<List<MovieEntity>>

    @Query("select * from MoviesTable")
    fun getMovies(): List<MovieEntity>

    @Insert
    fun addMovies(movie: MovieEntity)

    @Query("delete from MoviesTable")
    fun clear()

}