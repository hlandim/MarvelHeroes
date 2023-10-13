package com.hlandim.marvelheroes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.hlandim.marvelheroes.feature.comicsList.navigation.COMICS_LIST_ROUTE
import com.hlandim.marvelheroes.feature.heroesList.navigation.HEROES_LIST_ROUTE

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object HeroesList : Destinations(
        route = HEROES_LIST_ROUTE,
        title = "Heroes",
        icon = Icons.Outlined.Home
    )

    data object ComicLists : Destinations(
        route = COMICS_LIST_ROUTE,
        title = "Favorite",
        icon = Icons.Outlined.Favorite
    )
}
