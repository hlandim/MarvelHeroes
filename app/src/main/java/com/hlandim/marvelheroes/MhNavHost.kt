package com.hlandim.marvelheroes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hlandim.marvelheroes.feature.heroDetails.navigation.heroDetailsScreen
import com.hlandim.marvelheroes.feature.heroDetails.navigation.navigateToHeroDetails
import com.hlandim.marvelheroes.feature.heroesList.HeroesListRoute
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

private const val MAIN_VIEW_PAGE_ROUTE = "main_view_pager_route"

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MhNavHost(
    navController: NavHostController,
) {
    KoinAndroidContext {
        NavHost(navController = navController, startDestination = MAIN_VIEW_PAGE_ROUTE) {
            mainViewPager(navController)
            heroDetailsScreen()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun NavGraphBuilder.mainViewPager(navController: NavHostController) {
    composable(route = MAIN_VIEW_PAGE_ROUTE) {
        val pagerStatus = rememberPagerState(pageCount = { 3 })
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 2.dp, color = Color.Blue),
            state = pagerStatus,
        ) {
            when (it) {
                0 -> HeroesListRoute(
                    onHeroClicked = navController::navigateToHeroDetails
                )

                1 -> {
                    Text(text = "Page - 1")
                }

                2 -> {
                    Text(text = "Page - 2")
                }
            }
        }
    }
}
