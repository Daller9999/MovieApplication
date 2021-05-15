package com.sunplacestudio.movieapplication.fragment.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieRepositoryImpl
import com.sunplacestudio.movieapplication.utils.ApiKeyHelper
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.utils.NetworkUtils
import com.sunplacestudio.movieapplication.utils.apicall.MovieApiCall
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovieData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.java.KoinJavaComponent.inject
import java.util.function.Consumer

class MovieFragmentViewModelImpl(
    application: Application
): AndroidViewModel(application), MovieFragmentViewModel {

    private val movieRepository by inject(MovieRepositoryImpl::class.java)
    private val movieApiCall = MovieApiCall()
    private val apiKeyHelper by inject(ApiKeyHelper::class.java)

    private val movieCategoryListLiveData: MutableLiveData<List<MovieCategoryList>> = MutableLiveData()
    private val searchResult: MutableLiveData<Movie> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private val networkUtils = NetworkUtils(application.applicationContext)

    init {
        sendRequests()

        var disposable = movieRepository.getMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list -> movieCategoryListLiveData.postValue(list) }
        compositeDisposable.add(disposable)

        disposable = movieRepository.getMoviesLiveData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movieCategoryListLiveData.value = it }
        compositeDisposable.add(disposable)
    }

    override fun sendRequests() {
        if (!networkUtils.isConnected()) return

        val disposable = movieRepository.clear().doOnComplete {
            movieApiCall.requestMovieData(object : MovieApiCall.RequestMovies {
                override fun onRequestMovies(jsonMovie: JsonMovie, categoryMovie: CategoryMovie) {
                    val arrayList = ArrayList<Movie>()
                    for (jsonMovieData in jsonMovie.results) {
                        arrayList.add(Movie(
                            jsonMovieData.title,
                            apiKeyHelper.getImageUrl() + jsonMovieData.poster_path,
                            jsonMovieData.vote_average,
                            jsonMovieData.id))
                    }
                    compositeDisposable.add(movieRepository.addMovies(MovieCategoryList(categoryMovie.ordinal, arrayList)).subscribe())
                }
            })
        }.subscribe()
        compositeDisposable.add(disposable)
    }

    override fun getMovies(): LiveData<List<MovieCategoryList>> {
        return movieCategoryListLiveData
    }

    override fun getSearchResult(): LiveData<Movie> {
        return searchResult
    }

    override fun searchMovie(string: String) {
        if (!networkUtils.isConnected()) return

        movieApiCall.searchMovie(string, object : MovieApiCall.RequestSearch {
            override fun onSearchOver(jsonMovieData: JsonMovieData) {
                movieCategoryListLiveData.postValue(listOf(
                    MovieCategoryList(
                        CategoryMovie.RECOMMENDED.ordinal,
                        listOf(
                            Movie(
                                jsonMovieData.title,
                                apiKeyHelper.getImageUrl() + jsonMovieData.poster_path,
                                jsonMovieData.vote_average,
                                jsonMovieData.id
                            )
                        )
                    )
                ))
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}