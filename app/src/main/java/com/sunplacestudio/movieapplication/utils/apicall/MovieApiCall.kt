package com.sunplacestudio.movieapplication.utils.apicall

import com.sunplacestudio.movieapplication.utils.ApiKeyHelper
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovieData
import com.sunplacestudio.movieapplication.utils.apicall.json.movie.JsonMovie
import com.sunplacestudio.movieapplication.utils.apicall.json.search.JsonSearch
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class MovieApiCall {

    private val serverBaseUrl = "https://api.themoviedb.org"
    private val retrofit = Retrofit.Builder()
        .baseUrl(serverBaseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiCall::class.java)
    private val apiKeyHelper by inject(ApiKeyHelper::class.java)
    private val ioScheduler = Schedulers.io()

    fun requestMovieData(requestMovies: RequestMovies) {
        apiService
            .sendRequestPopular(apiKeyHelper.getApiKey())
            .delay(100, TimeUnit.MILLISECONDS)
            .flatMap {
                requestMovies.onRequestMovies(it, CategoryMovie.RECOMMENDED)
                return@flatMap apiService.sendRequestNowPlaying(apiKeyHelper.getApiKey())
            }
            .map { requestMovies.onRequestMovies(it, CategoryMovie.POPULAR) }
            .subscribeOn(ioScheduler)
            .subscribe()

    }

    fun searchMovie(string: String, requestSearch: RequestSearch) {
        apiService
            .searchMovie(apiKeyHelper.getApiKey(), string)
            .flatMap {jsonSearch ->
                if (jsonSearch.results.isNotEmpty()) {
                    return@flatMap Observable.just(jsonSearch.results[0].id)
                }
                return@flatMap Observable.just(-1)
            }
            .flatMap { id ->
                if (id == -1) {
                    return@flatMap Observable.just(JsonMovieData("", 0.0f, -1, ""))
                }
                return@flatMap apiService.getMovieInfo(id, apiKeyHelper.getApiKey(), "ru-RU")
            }
            .doOnNext {
                requestSearch.onSearchOver(it)
            }
            .subscribeOn(ioScheduler)
            .subscribe()
    }

    interface RequestMovies {
        fun onRequestMovies(jsonMovie: JsonMovie, categoryMovie: CategoryMovie)
    }

    interface RequestSearch {
        fun onSearchOver(jsonMovieData: JsonMovieData)
    }

    private interface ApiCall {
        @GET("3/movie/popular?language=ru-RU&page=1")
        fun sendRequestPopular(@Query("api_key") key: String): Observable<JsonMovie>

        @GET("3/movie/now_playing?language=ru-RU")
        fun sendRequestNowPlaying(@Query("api_key") key: String): Observable<JsonMovie>

        @GET("3/search/company")
        fun searchMovie(@Query("api_key") key: String, @Query("query") search: String): Observable<JsonSearch>

        fun getMovieInfo(@Field("movie") id: Int, @Query("api_key") key: String, @Query("language") lan: String): Observable<JsonMovieData>
    }
}