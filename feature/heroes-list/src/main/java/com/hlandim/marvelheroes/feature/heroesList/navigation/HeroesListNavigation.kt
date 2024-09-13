package com.hlandim.marvelheroes.feature.heroesList.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hlandim.marvelheroes.feature.heroesList.HeroesListScreen
import com.hlandim.marvelheroes.ui.BottomBarRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.heroesListScreen(
    onHeroClicked: (heroId: String) -> Unit,
) {
    composable<HeroesListRoute> {
        HeroesListScreen(onHeroClicked = {
            onHeroClicked(it)
        })
    }
}

@Serializable
data object HeroesListRoute : BottomBarRoute
