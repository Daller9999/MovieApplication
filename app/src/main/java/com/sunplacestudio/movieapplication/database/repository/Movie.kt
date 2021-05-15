package com.sunplacestudio.movieapplication.database.repository

import androidx.room.PrimaryKey

data class Movie(
    val name: String,
    val posterUrl: String,
    val voteAverage: Float,
    val idMovie: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)