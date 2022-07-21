package com.timife.yassirmovie.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trendingmoviesentity")
data class TrendingMoviesEntity(
    @PrimaryKey val id: Int? = null,
    val original_language: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double
)