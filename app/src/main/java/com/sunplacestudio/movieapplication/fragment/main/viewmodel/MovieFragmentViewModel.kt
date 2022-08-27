package com.sunplacestudio.movieapplication.fragment.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MovieFragmentViewModel(
    private val movieRepository: MovieRepository,
    private val movieApiCall: MovieApiCall,
    private val apiHelper: ApiHelper,
    private val networkUtils: NetworkUtils
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
                        convertJsonMovieToMovieCategoryList(jsonMovie, categoryMovie)
                    ).subscribe()
                )
            }
        }.subscribe()
        compositeDisposable.add(disposable)
    }

    fun getMovies(): LiveData<List<MovieCategoryList>> {
        return movieCategoryListLiveData
    }

    fun searchMovie(string: String) {
        if (!networkUtils.isConnected()) return

        if (string.isNotEmpty()) {
            movieApiCall.searchMovie(string) {
                val listMovieCategoryList =
                    listOf(convertJsonMovieToMovieCategoryList(it, CategoryMovie.FOUND))
                movieCategoryListLiveData.postValue(listMovieCategoryList)
            }
        } else {
            sendRequests()
        }
    }

    private fun convertJsonMovieToMovieCategoryList(
        jsonMovie: JsonMovie,
        categoryMovie: CategoryMovie
    ): MovieCategoryList {
        val arrayList = ArrayList<Movie>()
        var movieData: Movie
        for (jsonMovieData in jsonMovie.results) {
            movieData = Movie(
                jsonMovieData.title,
                apiHelper.getImageUrl() + jsonMovieData.poster_path,
                jsonMovieData.vote_average,
                jsonMovieData.id,
                jsonMovieData.overview
            )
            arrayList.add(movieData)
        }
        return MovieCategoryList(categoryMovie.ordinal, arrayList)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}