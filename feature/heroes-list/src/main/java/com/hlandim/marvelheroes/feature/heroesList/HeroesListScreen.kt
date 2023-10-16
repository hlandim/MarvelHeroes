package com.hlandim.marvelheroes.feature.heroesList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.component.ErrorDialog
import com.hlandim.marvelheroes.ui.component.shimmerBrush
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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

            HeroesListUiState.Loading -> HeroesGridList(
                heroes = remember { persistentListOf() }
            )
        }
    }
}

@Composable
private fun HeroesGridList(
    heroes: ImmutableList<Hero>,
    onHeroClicked: (String) -> Unit = {},
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = minCardHeight),
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
    ) {
        if (heroes.isEmpty()) {
            items(PAGING_SIZE) {
                HeroCardLoading()
            }
        } else {
            items(heroes) {
                HeroCard(
                    modifier = Modifier
                        .clickable { onHeroClicked(it.id.toString()) }
                        .fillMaxSize(),
                    hero = it,
                )
            }
        }
    }
}

@Composable
private fun HeroCard(
    modifier: Modifier,
    hero: Hero,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column {
            val showShimmer = remember { mutableStateOf(true) }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(hero.thumbnailUrl)
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minCardHeight)
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = showShimmer.value,
                        )
                    ),
                onSuccess = { showShimmer.value = false },
            )
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = showShimmer.value,
                        )
                    ),
                textAlign = TextAlign.Center,
                text = hero.name,
            )
        }
    }
}

@Composable
private fun HeroCardLoading() {
    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minCardHeight)
                .background(
                    shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = true,
                    )
                )
        )
        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(
                    shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = true,
                    )
                )
        )
    }
}

private val minCardHeight = 128.dp
