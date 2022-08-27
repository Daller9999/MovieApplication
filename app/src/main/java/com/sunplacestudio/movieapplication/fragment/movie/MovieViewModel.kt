package com.sunplacestudio.movieapplication.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieRepository
import com.sunplacestudio.movieapplication.utils.usecase.CurrentMovieUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(
    private val currentMovieUseCase: CurrentMovieUseCase
) : ViewModel() {

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    val movie: LiveData<Movie> = _movie

    fun uploadMovie() {
        currentMovieUseCase.movie?.let {
            _movie.postValue(it)
        }
    }
}