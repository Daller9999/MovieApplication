package com.sunplacestudio.movieapplication.utils

import com.sunplacestudio.movieapplication.BuildConfig

class ApiHelper {

    private val apiKey = BuildConfig.API_KEY

    private val imageUrl = "https://image.tmdb.org/t/p/w500"

    fun getApiKey(): String = apiKey

    fun getImageUrl() = imageUrl
}