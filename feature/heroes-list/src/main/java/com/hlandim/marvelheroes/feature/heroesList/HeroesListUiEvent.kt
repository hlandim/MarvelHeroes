package com.hlandim.marvelheroes.feature.heroesList

sealed interface HeroesListUiEvent {
    data object OnErrorDismissed : HeroesListUiEvent
    data object FetchNextListPage : HeroesListUiEvent
}
