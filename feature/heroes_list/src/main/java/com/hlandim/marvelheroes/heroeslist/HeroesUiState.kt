package com.hlandim.marvelheroes.heroeslist

import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.util.UiText

sealed interface HeroesUiState {
    data object Loading : HeroesUiState
    data class Found(
        val heroes: List<Hero>
    ) : HeroesUiState

    data class Error(val heroes: List<Hero>?, val message: UiText) : HeroesUiState
}
