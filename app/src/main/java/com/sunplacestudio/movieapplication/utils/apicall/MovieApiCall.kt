package com.sunplacestudio.movieapplication.utils.apicall

import com.google.gson.GsonBuilder
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*

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
        }
    }

    suspend fun requestNowPlayingMovie(page: Int): JsonMovie {
        val url = "$BASE_URL/3/movie/now_playing?language=$LAN&page=1"
        return client.request(url) {
            method = HttpMethod.Get
            parameter("api_key", apiKeyHelper.getApiKey())
            parameter("page", page)
        }
    }


    suspend fun searchMovie(string: String): JsonMovie {
        val url = "$BASE_URL/3/search/movie?language=$LAN&page=1&include_adult=true"
        return client.request(url) {
            method = HttpMethod.Get
            parameter("api_key", apiKeyHelper.getApiKey())
            parameter("query", string)
        }
    }

//    private interface ApiCall {
//        @GET("3/movie/popular?language=en-US&page=1")
//        fun sendRequestPopular(@Query("api_key") key: String): Observable<JsonMovie>
//
//        @GET("3/movie/now_playing?language=en-US")
//        fun sendRequestNowPlaying(@Query("api_key") key: String): Observable<JsonMovie>
//
//        @GET("3/movie/{movie_id}?language=en-US")
//        fun getMovieDetails(
//            @Query("api_key") key: String,
//            @Path("movie_id") id: Int
//        ): Observable<JsonMovie>
//
//        @GET("3/search/movie?language=en-US&page=1&include_adult=true")
//        fun searchMovie(
//            @Query("api_key") key: String,
//            @Query("query") search: String
//        ): Observable<JsonMovie>
//    }

}