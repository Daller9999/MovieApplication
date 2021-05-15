package com.sunplacestudio.movieapplication.database.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sunplacestudio.movieapplication.database.repository.Movie

class TypeConverter {

    @TypeConverter
    fun convertToStringList(list: List<Movie>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun convertFromStringToList(string: String): List<Movie> {
        val listType = object : TypeToken<List<Movie>>() {}.type
        return Gson().fromJson(string, listType)
    }

}