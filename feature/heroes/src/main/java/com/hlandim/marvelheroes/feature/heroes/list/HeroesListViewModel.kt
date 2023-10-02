package com.hlandim.marvelheroes.feature.heroes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.core.data.util.Resource
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val PAGING_SIZE = 20

class HeroesListViewModel(
    private val heroRepository: HeroRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HeroesListUiState> =
        MutableStateFlow(HeroesListUiState.Loading)
    val uiState: StateFlow<HeroesListUiState> = _uiState

    init {
        getHeroes()
    }

    private fun getHeroes() {
        viewModelScope.launch {
            heroRepository.getHeroes(
                offset = 0,
                limit = PAGING_SIZE,
                fetchFromRemote = false,
                query = "",
            ).collect { result ->
                when (result) {
                    is Resource.Success -> result.data?.let { heroes ->
                        updateState {
                            HeroesListUiState.Found(heroes)
                        }
                    }

                    is Resource.Error ->
                        updateState {
                            HeroesListUiState.Error(
                                heroes = result.data
                                    ?: if (_uiState.value is HeroesListUiState.Found) {
                                        (_uiState.value as HeroesListUiState.Found).heroes
                                    } else {
                                        emptyList()
                                    },
                                message = UiText.DynamicString(result.message.orEmpty())
                            )
                        }

                    is Resource.Loading -> updateState { HeroesListUiState.Loading }
                }
            }
        }
    }

    private fun updateState(update: HeroesListUiState.() -> HeroesListUiState) {
        _uiState.update { update(it) }
    }
}
