package com.sunplacestudio.movieapplication.database.repository

import androidx.room.PrimaryKey

data class Movie(
    val name: String,
    val posterUrl: String,
    val voteAverage: Float,
    val idMovie: Int,
    val overview: String,
    val releaseDate: String,
    val runtime: Int,
    val productionCompanies: List<ProductionCompany>,
    val genres: List<Genre>,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
) {
    fun starCount(): Int {
        val size = 5
        val count = (voteAverage / 2f).toInt()
        return if (count >= size) size - 1 else count
    }

    companion object {
        fun empty() = Movie("", "", 0f, -1, "", "", -1, emptyList(), emptyList())
    }
}

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