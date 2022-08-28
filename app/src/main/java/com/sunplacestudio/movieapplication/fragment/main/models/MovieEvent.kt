package com.sunplacestudio.movieapplication.fragment.main.models

import com.sunplacestudio.movieapplication.database.repository.Movie

sealed interface MovieEvent {

    object OnStartUI : MovieEvent

    object OnStopUI : MovieEvent

    data class OnSelectMovie(val movie: Movie) : MovieEvent

    data class OnSearchMovie(val string: String) : MovieEvent
}