package com.hlandim.marvelheroes.feature.heroesList.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hlandim.marvelheroes.feature.heroesList.HeroesListRoute

const val HEROES_LIST_ROUTE: String = "heroes_list_route"

fun NavGraphBuilder.heroesListScreen(
    onHeroClicked: (heroId: String) -> Unit,
) {
    composable(route = HEROES_LIST_ROUTE) {
        HeroesListRoute(onHeroClicked = {
            onHeroClicked(it)
        })
    }
}
