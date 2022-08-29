package com.sunplacestudio.movieapplication.data.apicall.json.movie

import com.sunplacestudio.movieapplication.utils.orNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonProductionCountry(
    @SerialName("name")
    private val _name: String? = null
) {
    val countyName = _name.orNull()
}
