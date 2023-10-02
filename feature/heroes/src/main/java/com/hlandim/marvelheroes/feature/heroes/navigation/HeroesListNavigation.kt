package com.hlandim.marvelheroes.feature.heroes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hlandim.marvelheroes.feature.heroes.details.HeroDetailsRoute
import com.hlandim.marvelheroes.feature.heroes.list.HeroesListRoute

const val HEROES_GRAPH_ROUTE_PATH = "heroes_graph"
private const val HEROES_LIST_ROUTE = "heroes_list_route"
private const val HERO_DETAILS_ROUTE = "hero_details_route"

fun NavController.navigateToHeroesList(navOptions: NavOptions? = null) {
    this.navigate(HEROES_GRAPH_ROUTE_PATH, navOptions)
}

fun NavGraphBuilder.heroesGraph() {
    navigation(
        route = HEROES_GRAPH_ROUTE_PATH,
        startDestination = HEROES_LIST_ROUTE,
    ) {
        composable(route = HEROES_LIST_ROUTE) {
            HeroesListRoute()
        }
        composable(route = HERO_DETAILS_ROUTE) {
            HeroDetailsRoute()
        }
    }
}
