@file:Suppress("UndocumentedPublicFunction")

package com.hlandim.marvelheroes.util.paginator

interface PagingManager<Key, Item> {
    suspend fun loadNextItems(searchQuery: String? = null)
    fun reset()
}
