package com.timife.yassirmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [TrendingMoviesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MovieDao
}