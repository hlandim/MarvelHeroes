package com.hlandim.marvelheroes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hlandim.marvelheroes.feature.comicsList.navigation.comicsListScreen
import com.hlandim.marvelheroes.feature.heroDetails.navigation.heroDetailsScreen
import com.hlandim.marvelheroes.feature.heroDetails.navigation.navigateToHeroDetails
import com.hlandim.marvelheroes.feature.heroesList.navigation.heroesListScreen

@Composable
fun MhNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarDestinations.HeroesList.route
    ) {
        heroesListScreen(navController::navigateToHeroDetails)
        comicsListScreen()
        heroDetailsScreen()
    }
}
