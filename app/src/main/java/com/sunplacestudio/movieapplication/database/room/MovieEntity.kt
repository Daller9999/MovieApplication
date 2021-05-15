package com.sunplacestudio.movieapplication.database.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunplacestudio.movieapplication.database.repository.Movie

@Entity(tableName = "MoviesTable")
data class MovieEntity(
    val nameCategory: Int,
    val listMovies: List<Movie>,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)