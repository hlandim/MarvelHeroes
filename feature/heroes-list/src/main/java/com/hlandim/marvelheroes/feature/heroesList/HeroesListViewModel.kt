package com.hlandim.marvelheroes.feature.heroesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.core.data.util.pagging.PagingManagerImpl
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HeroesListViewModel(
    private val heroRepository: HeroRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HeroesListUiState> =
        MutableStateFlow(HeroesListUiState())
    val uiState: StateFlow<HeroesListUiState> = _uiState

    private val pagingManger = PagingManagerImpl(
        initialKey = _uiState.value.heroes.size,
        onLoadUpdated = {
            updateState { copy(isLoadingNextPage = it) }
        },
        onRequest = { offset, _ ->
            when (
                val result = heroRepository.getHeroes(
                    offset = offset,
                    limit = PAGING_SIZE,
                    fetchFromRemote = false,
                    query = "",
                )
            ) {
                is DataResponse.Error -> {
                    updateState {
                        copy(genericErrorMsg = UiText.DynamicString(result.message))
                    }
                    emptyList()
                }

                is DataResponse.Exception -> {
                    updateState {
                        copy(genericErrorMsg = UiText.DynamicString(result.e.message.orEmpty()))
                    }
                    emptyList()
                }

                is DataResponse.Success -> result.data.orEmpty()
            }
        },
        getNextKey = {
            _uiState.value.heroes.size
        },
        onError = {},
        onSuccess = { items, _ ->
            updateState {
                copy(
                    heroes = (heroes + items).toPersistentList(),
                    isLoadingNextPage = false,
                    endReached = items.isEmpty(),
                )
            }
        },
    )

    init {
        fetchNextListPage()
    }

    fun onUiEvent(event: HeroesListUiEvent) {
        when (event) {
            HeroesListUiEvent.FetchNextListPage -> fetchNextListPage()
            HeroesListUiEvent.OnErrorDismissed -> updateState { copy(genericErrorMsg = null) }
        }
    }

    private fun fetchNextListPage() {
        viewModelScope.launch(dispatcher) {
            pagingManger.loadNextItems()
        }
    }

    private fun updateState(update: HeroesListUiState.() -> HeroesListUiState) {
        _uiState.update { update(it) }
    }
}
