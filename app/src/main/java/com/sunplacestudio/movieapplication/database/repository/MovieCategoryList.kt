package com.sunplacestudio.movieapplication.database.repository

import com.sunplacestudio.movieapplication.database.repository.Movie

data class MovieCategoryList(
    val category: Int,
    val list: List<Movie>
)