package com.sunplacestudio.movieapplication.fragment.movie.models

import com.sunplacestudio.movieapplication.database.repository.Movie

sealed interface MovieViewState {
    data class OnUploadMovie(val movie: Movie) : MovieViewState
}