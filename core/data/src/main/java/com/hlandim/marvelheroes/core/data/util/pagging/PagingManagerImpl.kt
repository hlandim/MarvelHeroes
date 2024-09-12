package com.hlandim.marvelheroes.core.data.util.pagging

class PagingManagerImpl<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key, searchQuery: String?) -> List<Item>,
    private val getNextKey: (List<Item>) -> Key,
    private val onError: (Throwable?) -> Unit,
    private val onSuccess: (items: List<Item>, newKey: Key) -> Unit
) : PagingManager<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    @Suppress("TooGenericExceptionCaught")
    override suspend fun loadNextItems(searchQuery: String?) {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        var result: List<Item> = emptyList()
        try {
            result = onRequest(currentKey, searchQuery)
        } catch (e: Exception) {
            onError(e)
        }
        isMakingRequest = false
        onSuccess(result, currentKey)
        currentKey = getNextKey(result)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}
