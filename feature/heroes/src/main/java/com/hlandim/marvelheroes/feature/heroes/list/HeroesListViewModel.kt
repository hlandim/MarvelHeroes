package com.hlandim.marvelheroes.feature.heroes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.core.data.util.Resource
import com.hlandim.marvelheroes.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGING_SIZE = 20

@HiltViewModel
class HeroesListViewModel @Inject constructor(
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
                0,
                PAGING_SIZE,
                false,
                ""
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
