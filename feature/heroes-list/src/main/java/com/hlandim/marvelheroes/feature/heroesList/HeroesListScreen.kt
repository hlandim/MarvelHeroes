package com.hlandim.marvelheroes.feature.heroesList

import androidx.compose.foundation.clickable
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
    onHeroClicked: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Surface(Modifier.fillMaxSize()) {
        HeroesListScreen(uiState = uiState, onHeroClicked = onHeroClicked)
    }
}

@Composable
private fun HeroesListScreen(
    uiState: HeroesListUiState,
    onHeroClicked: (String) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        when (uiState) {
            is HeroesListUiState.Error -> {
                uiState.heroes?.let {
                    HeroesGridList(
                        heroes = it,
                        onHeroClicked = onHeroClicked,
                    )
                }
                ErrorDialog(
                    message = uiState.message.asString(),
                    onDismissRequest = { /*TODO*/ },
                    onConfirmClicked = { /*TODO*/ }
                )
            }

            is HeroesListUiState.Found -> HeroesGridList(
                heroes = uiState.heroes,
                onHeroClicked = onHeroClicked,
            )

            HeroesListUiState.Loading -> MhLoading()
        }
    }
}

@Composable
private fun HeroesGridList(
    heroes: List<Hero>,
    onHeroClicked: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(heroes) {
            HeroCard(
                modifier = Modifier.clickable { onHeroClicked(it.id.toString()) },
                hero = it,
            )
        }
    }
}

@Composable
private fun HeroCard(
    modifier: Modifier,
    hero: Hero,
) {
    Card(modifier) {
        Text(text = hero.name)
    }
}
