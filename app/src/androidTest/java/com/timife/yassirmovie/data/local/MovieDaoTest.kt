package com.timife.yassirmovie.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    //This rule tells junit to run one after another, not concurrently
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMovieItem() = runTest {
        val movieItem = TrendingMoviesEntity(1, "English", "com.app.jpg", "24-12-22","",0.0)
        val list = listOf(movieItem)
        dao.insertTrendingMovies(list)

        val allMovieItems = dao.getAllMovies()

        assertThat(allMovieItems).contains(movieItem)
    }

    @Test
    fun deleteMovieItems() = runTest {
        val movieItem = TrendingMoviesEntity(1, "English", "com.app.jpg", "24-12-22","",0.0)
        val list = listOf(movieItem)
        dao.insertTrendingMovies(list)

        dao.clearTrendingMovies()
        val allMovieItems = dao.getAllMovies()
        assertThat(allMovieItems).doesNotContain(movieItem)
    }
}
