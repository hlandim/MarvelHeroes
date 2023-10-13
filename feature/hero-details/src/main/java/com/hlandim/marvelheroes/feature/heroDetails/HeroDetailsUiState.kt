package com.hlandim.marvelheroes.feature.heroDetails

import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.util.UiText

sealed interface HeroDetailsUiState {
    data object Loading : HeroDetailsUiState
    data class Found(val hero: Hero) : HeroDetailsUiState

    data class Error(val message: UiText) : HeroDetailsUiState
}
