package com.sunplacestudio.movieapplication.database.repository

import android.content.Context
import androidx.room.PrimaryKey
import com.sunplacestudio.movieapplication.R

data class Movie(
    val name: String,
    val posterUrl: String,
    val voteAverage: Float,
    val idMovie: Int,
    val backdropPath: String,
    val overview: String,
    val releaseDate: String,
    val runtime: Int,
    val revenue: Int,
    val productionCompanies: List<ProductionCompany>,
    val productionCountry: List<ProductionCountry>,
    val genres: List<Genre>,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
) {
    fun starCount(): Int {
        val size = 5
        val count = (voteAverage / 2f).toInt()
        return if (count >= size) size - 1 else count
    }

    fun formatRuntime(context: Context): String {
        val hour = runtime / 60
        val min = runtime % 60
        return "$hour${context.getString(R.string.h)} $min${context.getString(R.string.min)}"
    }

    fun getCountryFormatted(): String {
        val stringBuilder = StringBuilder()
        productionCountry.withIndex().forEach {
            stringBuilder.append(it.value.name)
            if (it.index + 1 < productionCountry.size) {
                stringBuilder.append(", ")
            }
        }
        return stringBuilder.toString()
    }

    fun formatRevenue(): String {
        val stringBuilder = StringBuilder()
        val string = revenue.toString()
        string.reversed().withIndex().forEach {
            stringBuilder.append(it.value)
            if ((it.index + 1) % 3 == 0 && it.index > 0 && it.index + 1 < string.length) {
                stringBuilder.append(".")
            }
        }
        return stringBuilder.toString().reversed()
    }

    companion object {
        fun empty() =
            Movie("", "", 0f, -1, "", "", "", -1, -1, emptyList(), emptyList(), emptyList())
    }
}

data class ProductionCountry(
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val name: String? = null,
    val logoPath: String? = null,
    val originCountry: String? = null
)

data class Genre(
    val id: Int,
    val name: String
)