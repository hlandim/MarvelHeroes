package com.hlandim.marvelheroes.feature.heroes.list

import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.util.UiText

sealed interface HeroesListUiState {
    data object Loading : HeroesListUiState
    data class Found(
        val heroes: List<Hero>
    ) : HeroesListUiState

    data class Error(val heroes: List<Hero>?, val message: UiText) : HeroesListUiState
}
