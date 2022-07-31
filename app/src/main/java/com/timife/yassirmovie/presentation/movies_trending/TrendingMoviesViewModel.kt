package com.timife.yassirmovie.presentation.movies_trending

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.yassirmovie.domain.model.TrendingMovie
import com.timife.yassirmovie.domain.repositories.MoviesRepository
import com.timife.yassirmovie.presentation.movies_trending.pagination.DefaultPaginator
import com.timife.yassirmovie.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    var state by mutableStateOf(TrendingMoviesState())

//    init {
//        getMovies()
//    }

    fun onMovieEvent(event: TrendingMoviesEvent) {
        when (event) {
            is TrendingMoviesEvent.Refresh -> {
//                getMovies(2)
//                viewModelScope.launch {
//                   var test =  moviesRepository.getMovies(true,state.page)
//
//                    when(test){
//                        is Resource.Success -> {
//                            test.data?.let { movies ->
//                                state = state.copy(movies = movies, error = null)
//                            }
//                        }
//                    }
//                }
                state = state.copy(page = 0)
                loadNextItems()
//                getMovies(true)
            }
        }
    }


    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = {nextPage ->
            moviesRepository.getMoviesList(true,nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = { _, message ->
            state = state.copy(error = message, isLoading = false )
        },
        onSuccess = {items, newKey ->
            state = state.copy(
                movies = state.movies + items,
                page = newKey,
                endReached = items.isEmpty(),
            )
        }

    )
    init {
        loadNextItems()
    }


    fun loadNextItems(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun getMovies(fetchFromRemote:Boolean=false,page:Int=1) {
        viewModelScope.launch {
            moviesRepository.getMoviesList(fetchFromRemote,page).collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { movies ->
                            state = state.copy(movies = movies, error = null)
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(error = it.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = it.isLoading, error = null)
                    }
                }
            }
        }
    }
}