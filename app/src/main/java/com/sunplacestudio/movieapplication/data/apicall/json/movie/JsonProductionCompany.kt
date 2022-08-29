package com.sunplacestudio.movieapplication.data.apicall.json.movie

import com.sunplacestudio.movieapplication.utils.orNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonProductionCompany(
    @SerialName("id")
    private val _id: Int? = null,
    @SerialName("name")
    private val _name: String? = null,
    @SerialName("logo_path")
    private val _logoPath: String? = null,
    @SerialName("origin_country")
    private val _originCountry: String? = null
) {
    val ID = _id.orNull()
    val movieName = _name.orNull()
    val logoPath = _logoPath.orNull()
    val originCountry = _originCountry.orNull()
}