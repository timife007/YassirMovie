package com.timife.yassirmovie.presentation.movies_trending.pagination

interface Paginator<Key,Item> {
    suspend fun loadNextItems()
    fun reset()
}