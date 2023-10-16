package com.hlandim.marvelheroes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hlandim.marvelheroes.feature.comicsList.navigation.comicsListScreen
import com.hlandim.marvelheroes.feature.heroDetails.navigation.heroDetailsScreen
import com.hlandim.marvelheroes.feature.heroDetails.navigation.navigateToHeroDetails
import com.hlandim.marvelheroes.feature.heroesList.navigation.heroesListScreen
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MhNavHost(
    navController: NavHostController,
) {
    KoinAndroidContext {
        NavHost(
            navController = navController,
            startDestination = BottomBarDestinations.HeroesList.route
        ) {
            heroesListScreen(navController::navigateToHeroDetails)
            comicsListScreen()
            heroDetailsScreen()
        }
    }
}
