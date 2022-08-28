package com.sunplacestudio.movieapplication.fragment.main.models

import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList

sealed interface MovieViewState {
    data class OnUploadMovie(val list: List<MovieCategoryList>) : MovieViewState
}