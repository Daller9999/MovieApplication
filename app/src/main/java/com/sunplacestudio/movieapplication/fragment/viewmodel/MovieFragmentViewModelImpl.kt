package com.sunplacestudio.movieapplication.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieRepositoryImpl
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.utils.NetworkUtils
import com.sunplacestudio.movieapplication.utils.apicall.MovieApiCall
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.java.KoinJavaComponent.inject

class MovieFragmentViewModelImpl(
    application: Application
): AndroidViewModel(application), MovieFragmentViewModel {

    private val movieRepository by inject(MovieRepositoryImpl::class.java)
    private val movieApiCall = MovieApiCall(application.applicationContext)
    private val apiKeyHelper by inject(ApiHelper::class.java)

    private val movieCategoryListLiveData: MutableLiveData<List<MovieCategoryList>> = MutableLiveData()
    private val searchResult: MutableLiveData<Movie> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    private val networkUtils = NetworkUtils(application.applicationContext)

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

    override fun sendRequests() {
        if (!networkUtils.isConnected()) return

        val disposable = movieRepository.clear().doOnComplete {
            movieApiCall.requestMovieData { jsonMovie, categoryMovie ->
                val arrayList = ArrayList<Movie>()
                var movieData: Movie
                for (jsonMovieData in jsonMovie.results) {
                    movieData = Movie(
                        jsonMovieData.title,
                        apiKeyHelper.getImageUrl() + jsonMovieData.poster_path,
                        jsonMovieData.vote_average,
                        jsonMovieData.id)
                    arrayList.add(movieData)
                }
                compositeDisposable.add(movieRepository.addMovies(MovieCategoryList(categoryMovie.ordinal, arrayList)).subscribe())
            }
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

        movieApiCall.searchMovie(string) {
            val movieData = Movie(it.title, apiKeyHelper.getImageUrl() + it.poster_path, it.vote_average, it.id)
            val listMovieCategoryList = listOf(MovieCategoryList(CategoryMovie.RECOMMENDED.ordinal, listOf(movieData)))
            movieCategoryListLiveData.postValue(listMovieCategoryList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}