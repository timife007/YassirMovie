package com.timife.yassirmovie.presentation.movies_trending

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.yassirmovie.domain.repositories.MoviesRepository
import com.timife.yassirmovie.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    var state by mutableStateOf(TrendingMoviesState())

    init {
        getMovies()
    }

    fun onMovieEvent(event: TrendingMoviesEvent) {
        when (event) {
            is TrendingMoviesEvent.Refresh -> {
                getMovies(fetchFromRemote = true)
            }
        }
    }

    private fun getMovies(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            moviesRepository.getMoviesList(fetchFromRemote).collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { movies ->
                            state = state.copy(movies = movies)
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(error = it.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = it.isLoading)
                    }
                }
            }
        }
    }
}