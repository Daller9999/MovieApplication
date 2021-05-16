package com.sunplacestudio.movieapplication.utils.apicall

import android.content.Context
import com.sunplacestudio.movieapplication.R
import com.sunplacestudio.movieapplication.utils.ApiHelper
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovieData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class MovieApiCall(context: Context) {

    private val serverBaseUrl = "https://api.themoviedb.org"
    private val retrofit = Retrofit.Builder()
        .baseUrl(serverBaseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiCall::class.java)
    private val apiKeyHelper by inject(ApiHelper::class.java)
    private val ioScheduler = Schedulers.io()

    private val nothingFound = context.resources.getString(R.string.not_found)

    fun requestMovieData(onRequestMovies: (jsonMovie: JsonMovie, categoryMovie: CategoryMovie) -> Unit) {
        apiService
            .sendRequestPopular(apiKeyHelper.getApiKey())
            .onErrorReturn { JsonMovie(listOf()) }
            .doOnError { it.printStackTrace() }
            .delay(250, TimeUnit.MILLISECONDS)
            .flatMap {
                onRequestMovies(it, CategoryMovie.RECOMMENDED)
                return@flatMap apiService.sendRequestNowPlaying(apiKeyHelper.getApiKey())
            }
            .onErrorReturn { JsonMovie(listOf()) }
            .doOnError { it.printStackTrace() }
            .map { onRequestMovies(it, CategoryMovie.POPULAR) }
            .subscribeOn(ioScheduler)
            .subscribe()
    }


    fun searchMovie(string: String, onSearchOver: (jsonMovie: JsonMovie) -> Unit) {
        apiService
            .searchMovie(apiKeyHelper.getApiKey(), string)
            .onErrorReturn { JsonMovie(listOf(JsonMovieData(nothingFound, 0f, -1, ""))) }
            .delay(250, TimeUnit.MILLISECONDS)
            .doOnNext {
                onSearchOver(it)
            }
            .subscribeOn(ioScheduler)
            .subscribe()
    }

    private interface ApiCall {
        @GET("3/movie/popular?language=ru-RU&page=1")
        fun sendRequestPopular(@Query("api_key") key: String): Observable<JsonMovie>

        @GET("3/movie/now_playing?language=ru-RU")
        fun sendRequestNowPlaying(@Query("api_key") key: String): Observable<JsonMovie>

        @GET("3/search/movie?language=ru-RU&page=1&include_adult=true")
        fun searchMovie(@Query("api_key") key: String, @Query("query") search: String): Observable<JsonMovie>
    }
}