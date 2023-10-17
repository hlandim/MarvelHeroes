package com.hlandim.marvelheroes.feature.heroesList

import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

const val PAGING_SIZE: Int = 20

data class HeroesListUiState(
    val heroes: ImmutableList<Hero> = persistentListOf(),
    val isLoadingNextPage: Boolean = false,
    val endReached: Boolean = false,
    val genericErrorMsg: UiText? = null,
)
