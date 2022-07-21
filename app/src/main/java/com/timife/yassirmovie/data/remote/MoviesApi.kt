package com.timife.yassirmovie.data.remote

import com.timife.yassirmovie.BuildConfig
import com.timife.yassirmovie.data.remote.model.movie_detail.MovieDetailDto
import com.timife.yassirmovie.data.remote.model.movie_detail.cast.Credits
import com.timife.yassirmovie.data.remote.model.trendinglist.TrendingList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = BuildConfig.APIKEY
    ): TrendingList

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.APIKEY
    ): MovieDetailDto

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCast(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.APIKEY,
    ):Credits

    companion object {
        const val BASE_URL_LIST = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}