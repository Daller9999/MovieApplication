package com.sunplacestudio.movieapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sunplacestudio.movieapplication.database.repository.Genre
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.database.repository.ProductionCompany
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonGenre
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovieData
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonProductionCompany

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun JsonMovie.toMovieData(apiHelper: ApiHelper, category: CategoryMovie): MovieCategoryList {
    val arrayList = ArrayList<Movie>()
    resultList.forEach { arrayList.add(it.toMovie(apiHelper)) }
    return MovieCategoryList(category.ordinal, arrayList)
}

fun JsonMovieData.toMovie(apiHelper: ApiHelper) = Movie(
    name = movieTitle,
    posterUrl = apiHelper.getImageUrl() + posterPath,
    voteAverage = voteAverage,
    idMovie = ID,
    overview = movieOverview,
    releaseDate = releaseDate,
    runtime = movieRuntime,
    productionCompanies = productionCompanies.toListProductionCompany(),
    genres = genresMovie.toListGenre()
)

fun JsonProductionCompany.toProductionCompany(): ProductionCompany = ProductionCompany(
    id = ID,
    name = movieName,
    logoPath = logoPath,
    originCountry = originCountry
)

fun JsonGenre.toGenre() = Genre(ID, genre)

fun List<JsonGenre>.toListGenre(): List<Genre> {
    val list = arrayListOf<Genre>()
    forEach { list.add(it.toGenre()) }
    return list
}

fun List<JsonProductionCompany>.toListProductionCompany(): List<ProductionCompany> {
    val list = arrayListOf<ProductionCompany>()
    forEach { list.add(it.toProductionCompany()) }
    return list
}