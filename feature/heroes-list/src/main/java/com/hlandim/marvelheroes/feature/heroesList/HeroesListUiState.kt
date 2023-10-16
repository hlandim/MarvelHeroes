package com.hlandim.marvelheroes.feature.heroesList

import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

const val PAGING_SIZE: Int = 20
sealed interface HeroesListUiState {
    data object Loading : HeroesListUiState
    data class Found(
        val heroes: ImmutableList<Hero> = persistentListOf()
    ) : HeroesListUiState

    data class Error(val heroes: ImmutableList<Hero>? = null, val message: UiText) :
        HeroesListUiState
}
