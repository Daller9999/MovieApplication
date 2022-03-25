package com.sunplacestudio.movieapplication.fragment.main.viewmodel

import androidx.lifecycle.LiveData
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList

interface MovieFragmentViewModel {

    fun getMovies(): LiveData<List<MovieCategoryList>>

    fun getSearchResult(): LiveData<Movie>

    fun searchMovie(string: String)

    fun sendRequests()
}