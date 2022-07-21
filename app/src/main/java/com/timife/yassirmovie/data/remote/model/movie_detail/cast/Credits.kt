package com.timife.yassirmovie.data.remote.model.movie_detail.cast


import com.squareup.moshi.Json

data class Credits(
    @field:Json(name = "cast")
    val cast: List<CastDto?>?,
    @field:Json(name = "crew")
    val crew: List<Crew?>?,
    @field:Json(name = "id")
    val id: Int?
)