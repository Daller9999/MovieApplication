package com.sunplacestudio.movieapplication.utils.apicall.json.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonGenre(
    @SerialName("id")
    private val _id: Int? = null,
    @SerialName("name")
    private val _name: String? = null
) {
    val ID = _id ?: -1
    val genre = _name ?: ""
}
