package com.hlandim.marvelheroes.feature.heroes.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.component.ErrorDialog
import com.hlandim.marvelheroes.ui.component.MhLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun HeroesListRoute(
    viewModel: HeroesListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Surface(Modifier.fillMaxSize()) {
        HeroesListScreen(uiState = uiState)
    }
}

@Composable
private fun HeroesListScreen(
    uiState: HeroesListUiState,
) {
    Box(Modifier.fillMaxSize()) {
        when (uiState) {
            is HeroesListUiState.Error -> {
                uiState.heroes?.let { HeroesGridList(heroes = it) }
                ErrorDialog(
                    message = uiState.message.asString(),
                    onDismissRequest = { /*TODO*/ },
                    onConfirmClicked = { /*TODO*/ }
                )
            }

            is HeroesListUiState.Found -> HeroesGridList(heroes = uiState.heroes)

            HeroesListUiState.Loading -> MhLoading()
        }
    }
}

@Composable
private fun HeroesGridList(
    heroes: List<Hero>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(heroes) {
            HeroCard(hero = it)
        }
    }
}

@Composable
private fun HeroCard(hero: Hero) {
    Card {
        Text(text = hero.name)
    }
}
