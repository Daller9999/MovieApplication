package com.sunplacestudio.movieapplication.utils.apicall.json.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonMovieData(
    @SerialName("title")
    private val _title: String? = null,
    @SerialName("vote_average")
    private val _voteAverage: Float? = null,
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
    @SerialName("genres")
    private val _genres: List<JsonGenre>? = null
) {
    companion object {
        fun empty() = JsonMovieData("", 0f, -1, "", "", "", -1, emptyList(), emptyList())
    }

    val movieRevenue = _revenue
    val movieTitle = _title ?: ""
    val voteAverage = _voteAverage ?: -1f
    val ID = _id ?: -1
    val movieOverview = _overview ?: ""
    val posterPath = _posterPath ?: ""
    val releaseDate = _releaseDate ?: ""
    val movieRuntime = _runtime ?: 0
    val productionCompanies = _productionCompanies ?: emptyList()
    val genresMovie = _genres ?: emptyList()
}