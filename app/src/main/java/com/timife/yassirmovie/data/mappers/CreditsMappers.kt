package com.timife.yassirmovie.data.mappers

import com.timife.yassirmovie.data.remote.model.movie_detail.cast.CastDto
import com.timife.yassirmovie.domain.model.Cast

fun CastDto.toCast(): Cast{
    return Cast(
        castId = castId ?: 0,
        character = character ?: "",
        name = name ?: "",
        profilePath = profilePath ?: ""
    )
}