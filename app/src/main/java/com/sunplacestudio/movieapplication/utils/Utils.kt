package com.sunplacestudio.movieapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun JsonMovie.toMovieData(apiHelper: ApiHelper, category: CategoryMovie): MovieCategoryList {
    val arrayList = ArrayList<Movie>()
    var movieData: Movie
    for (jsonMovieData in results) {
        movieData = Movie(
            jsonMovieData.title,
            apiHelper.getImageUrl() + jsonMovieData.poster_path,
            jsonMovieData.vote_average,
            jsonMovieData.id,
            jsonMovieData.overview
        )
        arrayList.add(movieData)
    }
    return MovieCategoryList(category.ordinal, arrayList)
}