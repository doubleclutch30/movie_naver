package com.lutawav.naver_movie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lutawav.naver_movie.data.db.dao.MovieDao
import com.lutawav.naver_movie.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}