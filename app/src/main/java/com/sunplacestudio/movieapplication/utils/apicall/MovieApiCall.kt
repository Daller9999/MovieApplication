package com.sunplacestudio.movieapplication.utils.apicall

import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovieData
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.reflect.Method

class MovieApiCall(
    private val apiKeyHelper: ApiHelper
) {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org"
        private const val LAN = "en-US"
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    init {
        client.config {
            expectSuccess = true
            defaultRequest {
                host = BASE_URL
            }
        }
    }

    suspend fun requestPopularMovie(page: Int): JsonMovie {
        val url = "$BASE_URL/3/movie/popular?language=$LAN&page=1"
        return client.request(url) {
            method = HttpMethod.Get
            parameter("api_key", apiKeyHelper.getApiKey())
            parameter("page", page)
            parameter("language", LAN)
        }
    }

    suspend fun requestNowPlayingMovie(page: Int): JsonMovie {
        val url = "$BASE_URL/3/movie/now_playing?language=$LAN&page=1"
        return client.request(url) {
            method = HttpMethod.Get
            parameter("api_key", apiKeyHelper.getApiKey())
            parameter("page", page)
            parameter("language", LAN)
        }
    }

    suspend fun searchMovie(string: String): JsonMovie {
        val url = "$BASE_URL/3/search/movie?language=$LAN&page=1&include_adult=true"
        return client.request(url) {
            method = HttpMethod.Get
            parameter("api_key", apiKeyHelper.getApiKey())
            parameter("query", string)
            parameter("language", LAN)
        }
    }

    suspend fun getMovieDetails(id: Int): JsonMovieData {
        val url = "$BASE_URL/3/movie/$id"
        return client.request(url) {
            method = HttpMethod.Get
            parameter("api_key", apiKeyHelper.getApiKey())
            parameter("language", LAN)
        }
    }

}