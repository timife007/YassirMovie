package com.timife.yassirmovie.presentation.movies_trending.pagination

import com.timife.yassirmovie.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest:suspend (nextKey: Key) -> Flow<Resource<List<Item>>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?, String) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {
    private var currentKey: Key = initialKey
    private var isMakingRequest = false
    override suspend fun loadNextItems() {
        if(isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        result.collect{
            if(it.data == null){
                onError(null,"")
                onLoadUpdated(false)
                return@collect
            }else{
                currentKey = getNextKey(it.data)
                onSuccess(it.data,currentKey)
                onLoadUpdated(false)
                return@collect
            }
        }
    }

    override fun reset() {
        currentKey = initialKey
    }

}