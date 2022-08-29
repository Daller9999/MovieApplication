package com.sunplacestudio.movieapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sunplacestudio.movieapplication.data.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.data.apicall.json.movie.*
import com.sunplacestudio.movieapplication.database.repository.*

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
    backdropPath = apiHelper.getImageUrl() + backdropPath,
    idMovie = ID,
    overview = movieOverview,
    releaseDate = releaseDate.formatRelease(),
    runtime = movieRuntime,
    revenue = movieRevenue,
    productionCountry = productionCountries.toListProductionCounty(),
    productionCompanies = productionCompanies.toListProductionCompany(),
    genres = genresMovie.toListGenre()
)

private fun String.formatRelease(): String {
    return split("-")[0]
}

fun JsonProductionCompany.toProductionCompany(): ProductionCompany = ProductionCompany(
    id = ID,
    name = movieName,
    logoPath = logoPath,
    originCountry = originCountry
)

private fun List<JsonProductionCountry>.toListProductionCounty(): List<ProductionCountry> {
    val list = arrayListOf<ProductionCountry>()
    forEach { list.add(it.toProductionCountry()) }
    return list
}

fun JsonProductionCountry.toProductionCountry() = ProductionCountry(countyName)

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