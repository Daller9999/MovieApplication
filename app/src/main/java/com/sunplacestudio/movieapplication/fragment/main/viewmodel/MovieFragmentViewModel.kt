package com.sunplacestudio.movieapplication.fragment.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.database.repository.MovieRepository
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.NetworkUtils
import com.sunplacestudio.movieapplication.utils.apicall.MovieApiCall
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import com.sunplacestudio.movieapplication.utils.toMovieData
import com.sunplacestudio.movieapplication.utils.usecase.CurrentMovieUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MovieFragmentViewModel(
    private val movieRepository: MovieRepository,
    private val movieApiCall: MovieApiCall,
    private val apiHelper: ApiHelper,
    private val networkUtils: NetworkUtils,
    private val currentMovieUseCase: CurrentMovieUseCase
) : ViewModel() {

    private val movieCategoryListLiveData: MutableLiveData<List<MovieCategoryList>> =
        MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        sendRequests()

        var disposable = movieRepository.getMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list -> movieCategoryListLiveData.value = list }
        compositeDisposable.add(disposable)

        disposable = movieRepository.getMoviesLiveData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movieCategoryListLiveData.value = it }
        compositeDisposable.add(disposable)
    }

    fun sendRequests() {
        if (!networkUtils.isConnected()) return

        val disposable = movieRepository.clear().doOnComplete {
            movieApiCall.requestMovieData { jsonMovie, categoryMovie ->
                compositeDisposable.add(
                    movieRepository.addMovies(
                        jsonMovie.toMovieData(apiHelper, categoryMovie)
                    ).subscribe()
                )
            }
        }.subscribe()
        compositeDisposable.add(disposable)
    }

    fun setCurrentMovie(movie: Movie) {
        currentMovieUseCase.movie = movie
    }

    fun getMovies(): LiveData<List<MovieCategoryList>> {
        return movieCategoryListLiveData
    }

    fun searchMovie(string: String) {
        if (!networkUtils.isConnected()) return

        if (string.isNotEmpty()) {
            movieApiCall.searchMovie(string) {
                val listMovieCategoryList = listOf(it.toMovieData(apiHelper, CategoryMovie.FOUND))
                movieCategoryListLiveData.postValue(listMovieCategoryList)
            }
        } else {
            sendRequests()
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}