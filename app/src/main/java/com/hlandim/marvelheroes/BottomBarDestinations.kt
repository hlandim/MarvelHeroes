package com.hlandim.marvelheroes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hlandim.marvelheroes.feature.comicsList.navigation.ComicsListRoute
import com.hlandim.marvelheroes.feature.heroesList.navigation.HeroesListRoute
import com.hlandim.marvelheroes.ui.BottomBarRoute

sealed class BottomBarDestinations(
    val route: BottomBarRoute,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    data object HeroesList : BottomBarDestinations(
        route = HeroesListRoute,
        title = R.string.bottom_bar_tab_heroes,
        icon = R.drawable.ic_hero,
    )

    data object ComicLists : BottomBarDestinations(
        route = ComicsListRoute,
        title = R.string.bottom_bar_tab_comics,
        icon = R.drawable.ic_comics,
    )
}
