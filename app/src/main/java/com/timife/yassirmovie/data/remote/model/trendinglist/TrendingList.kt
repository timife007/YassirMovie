package com.timife.yassirmovie.data.remote.model.trendinglist


import com.squareup.moshi.Json

data class TrendingList(
    @field:Json(name = "page")
    val page: Int?,
    @field:Json(name = "results")
    val moviesDto: List<MoviesDto>?,
    @field:Json(name = "total_pages")
    val totalPages: Int?,
    @field:Json(name = "total_results")
    val totalResults: Int?
)