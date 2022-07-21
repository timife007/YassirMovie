package com.timife.yassirmovie.data.remote.model.movie_detail


import com.squareup.moshi.Json

data class Genre(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "name")
    val name: String?
)