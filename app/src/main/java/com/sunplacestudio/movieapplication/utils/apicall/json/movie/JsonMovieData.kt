package com.sunplacestudio.movieapplication.utils.apicall.json.movie

data class JsonMovieData(
    val title: String,
    val vote_average: Float,
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val production_companies: List<JsonProductionCompany>,
    val genres: List<JsonGenre>
) {
    companion object {
        fun empty() = JsonMovieData("", 0f, -1, "", "", "", -1, emptyList(), emptyList())
    }
}