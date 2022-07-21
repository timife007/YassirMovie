package com.timife.yassirmovie.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(
        trendingMoviesEntities:List<TrendingMoviesEntity>
    )

    @Query("DELETE FROM trendingmoviesentity")
    suspend fun clearTrendingMovies()

    @Query("SELECT * FROM trendingmoviesentity")
    suspend fun getAllMovies():List<TrendingMoviesEntity>

}
