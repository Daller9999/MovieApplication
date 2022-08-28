package com.sunplacestudio.movieapplication.fragment.movie

import com.sunplacestudio.movieapplication.core.BaseFlowViewModel
import com.sunplacestudio.movieapplication.data.apicall.MovieApiCall
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.fragment.movie.models.MovieEvent
import com.sunplacestudio.movieapplication.fragment.movie.models.MovieViewState
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.toMovie
import kotlinx.coroutines.launch

class MovieViewModel(
    private val apiHelper: ApiHelper,
    private val movieApiCall: MovieApiCall
) : BaseFlowViewModel<MovieViewState, MovieEvent>(MovieViewState(Movie.empty())) {

    fun uploadMovie(id: Int) = scopeIO.launch {
        val info = movieApiCall.getMovieDetails(id)
        update { it.copy(movie = info.toMovie(apiHelper)) }
    }

    override fun obtainEvent(viewEvent: MovieEvent) {
        when (viewEvent) {
            is MovieEvent.OnSetMovie -> uploadMovie(viewEvent.id)
        }
    }
}