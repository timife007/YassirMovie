package com.timife.yassirmovie.data.repositories

import com.timife.yassirmovie.data.local.MovieDao
import com.timife.yassirmovie.data.mappers.toCast
import com.timife.yassirmovie.data.mappers.toMovieDetails
import com.timife.yassirmovie.data.mappers.toTrendingMovies
import com.timife.yassirmovie.data.mappers.toTrendingMoviesEntity
import com.timife.yassirmovie.data.remote.MoviesApi
import com.timife.yassirmovie.domain.model.Cast
import com.timife.yassirmovie.domain.model.MovieDetails
import com.timife.yassirmovie.domain.model.TrendingMovie
import com.timife.yassirmovie.domain.repositories.MoviesRepository
import com.timife.yassirmovie.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
    private val api: MoviesApi
) : MoviesRepository {
    override suspend fun getMoviesList(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<TrendingMovie>>> {

        return flow {
            emit(Resource.Loading(true))
            val localMovies = dao.getAllMovies()
            emit(Resource.Success(data = localMovies.map {
                it.toTrendingMovies()
            }))

            val isDbEmpty = localMovies.isEmpty()
            val shouldLoadFromDb = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromDb) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteVideos = try {
                api.getTrendingMovies().moviesDto?.map {
                    it.toTrendingMovies()
                }

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                null
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                null
            }

            remoteVideos?.let { videos ->
                dao.clearTrendingMovies()
                dao.insertTrendingMovies(
                    videos.map {
                        it.toTrendingMoviesEntity()
                    }
                )
                emit(Resource.Success(
                    data = dao.getAllMovies().map {
                        it.toTrendingMovies()
                    }

                ))
                emit(
                    Resource.Loading(
                        false
                    )
                )
            }
        }
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetails> {
        return try {
            val details = api.getMovieDetails(id = id)
            Resource.Success(details.toMovieDetails())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Error loading Movie Details")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Error loading Movie Details")
        }
    }

    override suspend fun getMovieCasts(id: Int): Resource<List<Cast>> {
        return try {
            val casts = api.getMovieCast(id = id)
            Resource.Success(casts.cast?.map {
                it?.toCast() ?: Cast(0, "", "", "")
            })
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load Movie casts")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load Movie casts")
        }
    }
}