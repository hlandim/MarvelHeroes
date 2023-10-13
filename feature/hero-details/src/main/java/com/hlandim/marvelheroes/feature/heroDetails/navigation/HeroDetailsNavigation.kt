package com.hlandim.marvelheroes.feature.heroDetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hlandim.marvelheroes.feature.heroDetails.HeroDetailsRoute
import java.net.URLDecoder
import java.net.URLEncoder

private const val HERO_ID_ARG = "heroIdArg"
private const val HERO_DETAILS_ROUTE = "hero_details_route"
private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

fun NavController.navigateToHeroDetails(heroId: String) {
    val encodedHeroId = URLEncoder.encode(heroId, URL_CHARACTER_ENCODING)
    this.navigate("$HERO_DETAILS_ROUTE/$encodedHeroId")
}

internal class HeroDetailsArgs(val heroId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            checkNotNull(savedStateHandle[HERO_ID_ARG]),
            URL_CHARACTER_ENCODING
        )
    )
}

fun NavGraphBuilder.heroDetailsScreen() {
    composable(
        route = "$HERO_DETAILS_ROUTE/{$HERO_ID_ARG}",
        arguments = listOf(navArgument(HERO_ID_ARG) { type = NavType.StringType })
    ) {
        HeroDetailsRoute()
    }
}
