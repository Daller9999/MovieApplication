package com.sunplacestudio.movieapplication.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunplacestudio.movieapplication.database.room.MovieDao
import com.sunplacestudio.movieapplication.database.room.MovieEntity
import com.sunplacestudio.movieapplication.database.room.TypeConverter

@TypeConverters(TypeConverter::class)
@Database(entities = [MovieEntity::class], version = 1)
abstract class DataBase:  RoomDatabase() {
    companion object {
        fun buildDataBase(application: Application): DataBase {
            return Room.databaseBuilder(application.applicationContext, DataBase::class.java, "DataBaseMovies").build()
        }
    }
    abstract fun getMovieDao(): MovieDao
}