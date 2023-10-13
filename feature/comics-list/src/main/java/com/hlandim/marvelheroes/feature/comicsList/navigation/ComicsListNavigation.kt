package com.hlandim.marvelheroes.feature.comicsList.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hlandim.marvelheroes.feature.comicsList.ComicsListScreen

const val COMICS_LIST_ROUTE = "COMICS_LIST_ROUTE"

fun NavGraphBuilder.comicsListScreen() {
    composable(route = COMICS_LIST_ROUTE) {
        ComicsListScreen()
    }
}
