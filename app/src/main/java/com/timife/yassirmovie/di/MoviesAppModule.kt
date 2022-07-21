package com.timife.yassirmovie.di

import android.app.Application
import androidx.room.Room
import com.timife.yassirmovie.data.local.MovieDao
import com.timife.yassirmovie.data.local.MovieDatabase
import com.timife.yassirmovie.data.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesAppModule {
    @Provides
    @Singleton
    fun provideMoviesApi():MoviesApi{
        return Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL_LIST)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(app:Application):MovieDatabase{
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movies.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(
        moviesDatabase: MovieDatabase
    ):MovieDao{
        return moviesDatabase.dao
    }
}