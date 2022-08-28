package com.sunplacestudio.movieapplication.fragment.movie

import android.app.Application
import com.sunplacestudio.movieapplication.core.BaseViewModel
import com.sunplacestudio.movieapplication.fragment.movie.models.MovieEvent
import com.sunplacestudio.movieapplication.fragment.movie.models.MovieViewState
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.apicall.MovieApiCall
import com.sunplacestudio.movieapplication.utils.toMovie
import kotlinx.coroutines.launch

class MovieViewModel(
    application: Application,
    private val apiHelper: ApiHelper,
    private val movieApiCall: MovieApiCall
) : BaseViewModel<MovieViewState, MovieEvent>(application) {

    fun uploadMovie(id: Int) = scopeIO.launch {
        val info = movieApiCall.getMovieDetails(id)
        viewState = MovieViewState.OnUploadMovie(info.toMovie(apiHelper))
    }

    override fun obtainEvent(viewEvent: MovieEvent) {
        when (viewEvent) {
            is MovieEvent.OnSetMovie -> uploadMovie(viewEvent.id)
        }
    }
}