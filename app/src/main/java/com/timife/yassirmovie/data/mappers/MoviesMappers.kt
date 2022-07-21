package com.timife.yassirmovie.data.mappers

import com.timife.yassirmovie.data.local.TrendingMoviesEntity
import com.timife.yassirmovie.data.remote.model.movie_detail.Genre
import com.timife.yassirmovie.data.remote.model.movie_detail.MovieDetailDto
import com.timife.yassirmovie.data.remote.model.trendinglist.MoviesDto
import com.timife.yassirmovie.domain.model.MovieDetails
import com.timife.yassirmovie.domain.model.TrendingMovie

fun TrendingMoviesEntity.toTrendingMovies():TrendingMovie{
    return TrendingMovie(
        id = id ?: 0,
        language = original_language,
        poster = poster_path,
        releaseDate = release_date,
        title = title,
        voteAverage = vote_average
    )
}

fun TrendingMovie.toTrendingMoviesEntity():TrendingMoviesEntity{
    return TrendingMoviesEntity(
        id = id,
        original_language = language,
        poster_path = poster,
        release_date = releaseDate,
        title = title,
        vote_average = voteAverage
    )
}

fun MoviesDto.toTrendingMovies():TrendingMovie{
    return TrendingMovie(
        id = id ?: 0,
        language = originalLanguage ?: "",
        poster = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0
    )
}

fun MovieDetailDto.toMovieDetails():MovieDetails{
    return MovieDetails(
        backdropPath = backdropPath ?: "",
        homepage = homepage ?: "",
        id = id ?: 0,
        posterPath = posterPath ?: "",
        title = title ?: "",
        language = originalLanguage ?: "",
        tagline = tagline ?: "",
        voteAverage = voteAverage ?: 0.0,
        overview = overview ?: "",
        runtime = runtime ?: 0,
        genres = genres?.map {
            it?.toGenre() ?: com.timife.yassirmovie.domain.model.Genre(0,"")
        } ?: listOf(),
        releaseDate = releaseDate ?: ""
    )
}


fun Genre.toGenre(): com.timife.yassirmovie.domain.model.Genre{
    return com.timife.yassirmovie.domain.model.Genre(
        id = id ?: 0,
        name = name ?: ""
    )
}
