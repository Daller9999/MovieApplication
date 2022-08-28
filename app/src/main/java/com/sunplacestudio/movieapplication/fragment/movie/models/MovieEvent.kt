package com.sunplacestudio.movieapplication.fragment.movie.models

sealed interface MovieEvent {
    data class OnSetMovie(val id: Int) : MovieEvent
}