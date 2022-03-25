package com.sunplacestudio.movieapplication.utils.apicall.json.movie

data class JsonMovieData(
    val title: String,
    val vote_average: Float,
    val id: Int,
    val overview: String,
    val poster_path: String
)