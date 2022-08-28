package com.sunplacestudio.movieapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovieData

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun JsonMovie.toMovieData(apiHelper: ApiHelper, category: CategoryMovie): MovieCategoryList {
    val arrayList = ArrayList<Movie>()
    var movieData: Movie
    for (jsonMovieData in resultList) {
        movieData = Movie(
            jsonMovieData.movieTitle,
            apiHelper.getImageUrl() + jsonMovieData.posterPath,
            jsonMovieData.voteAverage,
            jsonMovieData.ID,
            jsonMovieData.movieOverview
        )
        arrayList.add(movieData)
    }
    return MovieCategoryList(category.ordinal, arrayList)
}

fun JsonMovieData.toMovie(apiHelper: ApiHelper) = Movie(
    movieTitle,
    apiHelper.getImageUrl() + posterPath,
    voteAverage,
    ID,
    movieOverview
)