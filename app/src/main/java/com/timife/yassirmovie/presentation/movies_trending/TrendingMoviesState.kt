package com.timife.yassirmovie.presentation.movies_trending

import com.timife.yassirmovie.domain.model.TrendingMovie


data class TrendingMoviesState(
    val movies:List<TrendingMovie> = emptyList(),
    val isLoading:Boolean = false,
    val isRefreshing:Boolean = false,
    val error:String? = null,
    val endReached:Boolean = false,
    val page:Int = 1
)
