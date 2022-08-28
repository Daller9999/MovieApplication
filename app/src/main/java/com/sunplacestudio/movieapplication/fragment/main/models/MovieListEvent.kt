package com.sunplacestudio.movieapplication.fragment.main.models

import com.sunplacestudio.movieapplication.database.repository.Movie

sealed interface MovieListEvent {

    object OnStartUI : MovieListEvent

    object OnStopUI : MovieListEvent

    data class OnSearchMovie(val string: String) : MovieListEvent
}