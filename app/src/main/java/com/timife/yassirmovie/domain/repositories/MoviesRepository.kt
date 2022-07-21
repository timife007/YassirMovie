package com.timife.yassirmovie.domain.repositories

import com.timife.yassirmovie.domain.model.Cast
import com.timife.yassirmovie.domain.model.MovieDetails
import com.timife.yassirmovie.domain.model.TrendingMovie
import com.timife.yassirmovie.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesList(
        fetchFromRemote:Boolean
    ):Flow<Resource<List<TrendingMovie>>>

    suspend fun getMovieDetails(
        id:Int
    ):Resource<MovieDetails>

    suspend fun getMovieCasts(
        id:Int
    ):Resource<List<Cast>>
}