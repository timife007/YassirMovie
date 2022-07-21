package com.timife.yassirmovie.domain.model

data class TrendingMovie(
    val id: Int,
    val language: String,
    val poster: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double
)