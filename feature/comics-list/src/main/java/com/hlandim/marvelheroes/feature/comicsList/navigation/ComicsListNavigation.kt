package com.hlandim.marvelheroes.feature.comicsList.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hlandim.marvelheroes.feature.comicsList.ComicsListScreen
import com.hlandim.marvelheroes.ui.BottomBarRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.comicsListScreen() {
    composable<ComicsListRoute> {
        ComicsListScreen()
    }
}

@Serializable
data object ComicsListRoute : BottomBarRoute
