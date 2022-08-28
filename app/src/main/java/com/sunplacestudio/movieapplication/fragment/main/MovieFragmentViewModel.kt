package com.sunplacestudio.movieapplication.fragment.main

import android.app.Application
import com.sunplacestudio.movieapplication.core.BaseViewModel
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.database.repository.MovieRepository
import com.sunplacestudio.movieapplication.fragment.main.models.MovieListEvent
import com.sunplacestudio.movieapplication.fragment.main.models.MovieListViewState
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.NetworkUtils
import com.sunplacestudio.movieapplication.utils.apicall.MovieApiCall
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.toMovieData
import com.sunplacestudio.movieapplication.utils.usecase.CurrentMovieUseCase
import kotlinx.coroutines.launch

class MovieFragmentViewModel(
    application: Application,
    private val movieRepository: MovieRepository,
    private val movieApiCall: MovieApiCall,
    private val apiHelper: ApiHelper,
    private val networkUtils: NetworkUtils,
    private val currentMovieUseCase: CurrentMovieUseCase
) : BaseViewModel<MovieListViewState, MovieListEvent>(application) {

    private fun init() = scopeIO.launch {
        sendRequests()
        val list = movieRepository.getMovies()
        viewState = MovieListViewState.OnUploadMovie(list)
    }

    fun sendRequests() = scopeIO.launch {
        if (!networkUtils.isConnected()) return@launch
        val listCategory = arrayListOf<MovieCategoryList>()
        listCategory.add(
            movieApiCall.requestNowPlayingMovie(1).toMovieData(
                apiHelper, CategoryMovie.RECOMMENDED
            )
        )
        listCategory.add(
            movieApiCall.requestPopularMovie(1).toMovieData(
                apiHelper, CategoryMovie.POPULAR
            )
        )
        viewState = MovieListViewState.OnUploadMovie(listCategory)
    }

    fun setCurrentMovie(movie: Movie) {
        currentMovieUseCase.movie = movie
    }

    fun searchMovie(string: String) = scopeIO.launch {
        if (!networkUtils.isConnected()) return@launch
        if (string.isNotEmpty()) {
            val list = movieApiCall.searchMovie(string).toMovieData(apiHelper, CategoryMovie.FOUND)
            viewState = MovieListViewState.OnUploadMovie(listOf(list))
        } else {
            sendRequests()
        }
    }

    override fun obtainEvent(viewEvent: MovieListEvent) {
        when (viewEvent) {
            is MovieListEvent.OnStartUI -> init()
            is MovieListEvent.OnSelectMovie -> setCurrentMovie(viewEvent.movie)
            MovieListEvent.OnStopUI -> {}
            is MovieListEvent.OnSearchMovie -> searchMovie(viewEvent.string)
        }
    }

}