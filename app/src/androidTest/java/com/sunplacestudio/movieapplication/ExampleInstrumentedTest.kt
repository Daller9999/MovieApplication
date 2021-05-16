package com.sunplacestudio.movieapplication

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunplacestudio.movieapplication.utils.apicall.MovieApiCall

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sunplacestudio.movieapplication", appContext.packageName)
    }

    @Test
    fun requestTest() {
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