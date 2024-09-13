package com.hlandim.marvelheroes.feature.heroesList

import com.hlandim.marvelheroes.ui.component.GridListData
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

const val PAGING_SIZE: Int = 20

data class HeroesListUiState(
    val uiList: ImmutableList<GridListData> = persistentListOf(),
    val isLoadingNextPage: Boolean = false,
    val endReached: Boolean = false,
    val genericErrorMsg: UiText? = null,
)
