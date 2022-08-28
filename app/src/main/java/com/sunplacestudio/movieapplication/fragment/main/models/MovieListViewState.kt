package com.sunplacestudio.movieapplication.fragment.main.models

import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList

sealed interface MovieListViewState {
    data class OnUploadMovie(val list: List<MovieCategoryList>) : MovieListViewState
}