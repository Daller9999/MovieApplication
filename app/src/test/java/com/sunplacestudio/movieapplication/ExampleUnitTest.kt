package com.sunplacestudio.movieapplication

import androidx.test.platform.app.InstrumentationRegistry
import com.sunplacestudio.movieapplication.data.apicall.MovieApiCall
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val movieApiCall = MovieApiCall(appContext)
        movieApiCall.requestMovieData { jsonMovie, categoryMovie ->
            print(categoryMovie)
            print(jsonMovie)
        }
        movieApiCall.searchMovie("шрек") {
            print(it)
        }
    }
}