package com.sunplacestudio.movieapplication.fragment.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(
    application: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(application) {

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    val movie: LiveData<Movie> = _movie

    private val compositeDisposable = CompositeDisposable()

    fun uploadMovie(id: Int) {
        val disposable = movieRepository
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                for (data in it) {
                    for (movie in data.list) {
                        if (movie.idMovie == id) {
                            _movie.postValue(movie)
                        }
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