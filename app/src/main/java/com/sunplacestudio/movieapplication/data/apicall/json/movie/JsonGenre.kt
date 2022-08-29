package com.sunplacestudio.movieapplication.data.apicall.json.movie

import com.sunplacestudio.movieapplication.utils.orNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonGenre(
    @SerialName("id")
    private val _id: Int? = null,
    @SerialName("name")
    private val _name: String? = null
) {
    val ID = _id.orNull()
    val genre = _name.orNull()
}
