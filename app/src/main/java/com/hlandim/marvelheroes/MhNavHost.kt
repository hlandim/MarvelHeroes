package com.hlandim.marvelheroes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hlandim.marvelheroes.feature.heroes.navigation.HEROES_GRAPH_ROUTE_PATH
import com.hlandim.marvelheroes.feature.heroes.navigation.heroesGraph

@Composable
fun MhNavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = HEROES_GRAPH_ROUTE_PATH) {
        heroesGraph()
    }
}
