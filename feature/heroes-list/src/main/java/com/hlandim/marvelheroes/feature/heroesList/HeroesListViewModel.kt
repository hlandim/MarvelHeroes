package com.hlandim.marvelheroes.feature.heroesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.collections.immutable.persistentListOf
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
        MutableStateFlow(HeroesListUiState.Loading)
    val uiState: StateFlow<HeroesListUiState> = _uiState

    init {
        getHeroes()
    }

    private fun getHeroes() {
        viewModelScope.launch(dispatcher) {
            heroRepository.getHeroes(
                offset = 0,
                limit = PAGING_SIZE,
                fetchFromRemote = false,
                query = "",
            ).collect { result ->
                when (result) {
                    is DataResponse.Success -> result.data?.let { heroes ->
                        updateState {
                            HeroesListUiState.Found(heroes.toPersistentList())
                        }
                    }

                    is DataResponse.Error ->
                        updateState {
                            HeroesListUiState.Error(
                                heroes = result.data?.toPersistentList()
                                    ?: if (_uiState.value is HeroesListUiState.Found) {
                                        (_uiState.value as HeroesListUiState.Found).heroes
                                    } else {
                                        persistentListOf()
                                    },
                                message = UiText.DynamicString(result.message)
                            )
                        }

                    is DataResponse.Loading -> updateState { HeroesListUiState.Loading }
                    is DataResponse.Exception -> HeroesListUiState.Error(
                        message = UiText.DynamicString(
                            result.e.message.orEmpty()
                        )
                    )
                }
            }
        }
    }

    private fun updateState(update: HeroesListUiState.() -> HeroesListUiState) {
        _uiState.update { update(it) }
    }
}
