package com.hlandim.marvelheroes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hlandim.marvelheroes.feature.comicsList.navigation.COMICS_LIST_ROUTE
import com.hlandim.marvelheroes.feature.heroesList.navigation.HEROES_LIST_ROUTE

sealed class Destinations(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    data object HeroesList : Destinations(
        route = HEROES_LIST_ROUTE,
        title = R.string.bottom_bar_tab_heroes,
        icon = R.drawable.ic_hero,
    )

    data object ComicLists : Destinations(
        route = COMICS_LIST_ROUTE,
        title = R.string.bottom_bar_tab_comics,
        icon = R.drawable.ic_comics,
    )
}
