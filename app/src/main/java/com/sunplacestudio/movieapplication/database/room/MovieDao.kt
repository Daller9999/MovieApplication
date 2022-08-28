package com.sunplacestudio.movieapplication.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("select * from MoviesTable")
    fun getMoviesLiveData(): List<MovieEntity>

    @Query("select * from MoviesTable")
    fun getMovies(): List<MovieEntity>

    @Insert
    fun addMovies(movie: MovieEntity)

    @Query("delete from MoviesTable")
    fun clear()

}