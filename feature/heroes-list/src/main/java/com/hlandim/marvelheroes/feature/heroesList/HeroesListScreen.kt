package com.hlandim.marvelheroes.feature.heroesList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.component.ErrorDialog
import com.hlandim.marvelheroes.ui.component.MhGridCard
import com.hlandim.marvelheroes.ui.component.minMhCardHeight
import com.hlandim.marvelheroes.ui.theme.MhTheme
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
        HeroesListScreen(
            uiState = uiState,
            onHeroClicked = remember { onHeroClicked },
            onErrorDialogDismissed = remember { { viewModel.onUiEvent(HeroesListUiEvent.OnErrorDismissed) } },
            onFetchNextPage = remember { { viewModel.onUiEvent(HeroesListUiEvent.FetchNextListPage) } },
        )
    }
}

@Composable
private fun HeroesListScreen(
    uiState: HeroesListUiState,
    onHeroClicked: (String) -> Unit,
    onErrorDialogDismissed: () -> Unit,
    onFetchNextPage: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        HeroesGridList(
            heroes = uiState.heroes,
            onHeroClicked = onHeroClicked,
            onFetchNextPage = onFetchNextPage,
            isLoadingNextPage = uiState.isLoadingNextPage,
            endReached = uiState.endReached,
        )
        uiState.genericErrorMsg?.let {
            ErrorDialog(
                message = it.asString(),
                onDismissRequest = onErrorDialogDismissed,
                onConfirmClicked = onErrorDialogDismissed,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HeroesGridList(
    heroes: ImmutableList<Hero>,
    onHeroClicked: (String) -> Unit = {},
    onFetchNextPage: () -> Unit,
    endReached: Boolean,
    isLoadingNextPage: Boolean,
) {
    val size = if (heroes.isNotEmpty()) heroes.size else PAGING_SIZE
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = minMhCardHeight),
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp,
    ) {
        items(size, key = { it }) { index ->
            if (index >= size - 1 && !endReached && !isLoadingNextPage) {
                onFetchNextPage()
            }
            val hero = if (index <= heroes.size - 1) heroes[index] else null
            MhGridCard(
                modifier = Modifier
                    .animateItemPlacement()
                    .clickable {
                        hero?.let {
                            onHeroClicked(it.id.toString())
                        }
                    }
                    .fillMaxSize(),
                thumbnailUrl = hero?.thumbnailUrl,
                title = hero?.name.orEmpty(),
                isPlaceholder = hero == null,
            )
        }
        if (isLoadingNextPage) {
            item {
                MhGridCard(
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxSize(),
                    isPlaceholder = true,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HeroesListScreenPreview() {
    MhTheme {
        Surface {
            HeroesListScreen(
                uiState = HeroesListUiState(
                    heroes = persistentListOf(
                        Hero(
                            id = 1771,
                            name = "Osvaldo Morton",
                            thumbnailUrl = "",
                        ),
                        Hero(
                            id = 9300,
                            name = "Zachary Pruitt",
                            thumbnailUrl = "",
                        ),
                        Hero(
                            id = 3167,
                            name = "Dionne Soto",
                            thumbnailUrl = "",
                        ),
                        Hero(
                            id = 4280,
                            name = "Tisha Mueller",
                            thumbnailUrl = "",
                        ),
                        Hero(
                            id = 7884,
                            name = "Keisha McKnight",
                            thumbnailUrl = "",
                        ),
                        Hero(
                            id = 2230,
                            name = "Misty Carroll",
                            thumbnailUrl = "",
                        ),
                        Hero(
                            id = 8298,
                            name = "Vera Hull",
                            thumbnailUrl = "",
                        ),
                    )
                ),
                onHeroClicked = {},
                onErrorDialogDismissed = {},
                onFetchNextPage = {},
            )
        }
    }
}
