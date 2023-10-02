@file:Suppress("UndocumentedPublicFunction")

package com.hlandim.marvelheroes.core.data.util.pagging

interface PagingManager<Key, Item> {
    suspend fun loadNextItems(searchQuery: String? = null)
    fun reset()
}
