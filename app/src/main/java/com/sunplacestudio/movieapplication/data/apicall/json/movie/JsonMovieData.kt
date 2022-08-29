package com.sunplacestudio.movieapplication.data.apicall.json.movie

import com.sunplacestudio.movieapplication.utils.orNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonMovieData(
    @SerialName("title")
    private val _title: String? = null,
    @SerialName("vote_average")
    private val _voteAverage: Float? = null,
    @SerialName("backdrop_path")
    private val _backdropPath: String? = null,
    @SerialName("id")
    private val _id: Int? = null,
    @SerialName("overview")
    private val _overview: String? = null,
    @SerialName("poster_path")
    private val _posterPath: String? = null,
    @SerialName("release_date")
    private val _releaseDate: String? = null,
    @SerialName("revenue")
    private val _revenue: Int? = null,
    @SerialName("runtime")
    private val _runtime: Int? = null,
    @SerialName("production_companies")
    private val _productionCompanies: List<JsonProductionCompany>? = null,
    @SerialName("production_countries")
    private val _productionCountries: List<JsonProductionCountry>? = null,
    @SerialName("genres")
    private val _genres: List<JsonGenre>? = null
) {
    companion object {
        fun empty() = JsonMovieData("", 0f, "", -1, "", "", "", -1, -1, emptyList(), emptyList(), emptyList())
    }

    val backdropPath = _backdropPath.orNull()
    val movieRevenue = _revenue.orNull()
    val movieTitle = _title.orNull()
    val voteAverage = _voteAverage.orNull()
    val ID = _id.orNull()
    val movieOverview = _overview.orNull()
    val posterPath = _posterPath.orNull()
    val releaseDate = _releaseDate.orNull()
    val movieRuntime = _runtime.orNull()
    val productionCountries = _productionCountries.orNull()
    val productionCompanies = _productionCompanies.orNull()
    val genresMovie = _genres.orNull()
}