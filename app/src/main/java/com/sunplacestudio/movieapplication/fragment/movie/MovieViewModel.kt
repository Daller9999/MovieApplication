package com.sunplacestudio.movieapplication.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    val movie: LiveData<Movie> = _movie

    private val compositeDisposable = CompositeDisposable()

    fun uploadMovie(id: Int) {
        val disposable = movieRepository
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { list ->
                list.forEach { movieData ->
                    movieData.list.firstOrNull { movie ->
                        movie.idMovie == id
                    }?.let {
                        _movie.postValue(it)
                        return@forEach
                    }
                }
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}