package com.hlandim.marvelheroes.feature.heroDetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hlandim.marvelheroes.feature.heroDetails.HeroDetailsScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToHeroDetails(heroId: String) {
    this.navigate(HeroDetailsRoute(heroId = heroId))
}

fun NavGraphBuilder.heroDetailsScreen() {
    composable<HeroDetailsRoute> {
        HeroDetailsScreen()
    }
}

@Serializable
data class HeroDetailsRoute(val heroId: String)
