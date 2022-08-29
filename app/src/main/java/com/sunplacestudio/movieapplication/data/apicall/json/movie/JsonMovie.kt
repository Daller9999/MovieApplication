package com.sunplacestudio.movieapplication.data.apicall.json.movie

import com.sunplacestudio.movieapplication.utils.orNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonMovie(
    @SerialName("results")
    private val _resultsList: List<JsonMovieData>? = null
) {
    val resultList = _resultsList.orNull()
}