package com.timife.yassirmovie.presentation.movie_details

import com.timife.yassirmovie.domain.model.Cast
import com.timife.yassirmovie.domain.model.MovieDetails


data class MovieDetailsState(
    val movieDetails:MovieDetails? = null,
    val castsList: List<Cast> = emptyList(),
    val isLoading:Boolean  = false,
    val error:String? = null
)