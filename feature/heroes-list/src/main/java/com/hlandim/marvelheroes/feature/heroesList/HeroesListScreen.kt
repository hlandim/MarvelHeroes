package com.hlandim.marvelheroes.feature.heroesList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.component.ErrorDialog
import com.hlandim.marvelheroes.ui.component.shimmerBrush
import com.hlandim.marvelheroes.ui.theme.MhTheme
import kotlinx.collections.immutable.ImmutableList
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
            onHeroClicked = onHeroClicked,
            onFetchNextPage = viewModel::fetchNextListPage,
        )
    }
}

@Composable
private fun HeroesListScreen(
    uiState: HeroesListUiState,
    onHeroClicked: (String) -> Unit,
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
                onDismissRequest = { /*TODO*/ },
                onConfirmClicked = { /*TODO*/ },
            )
        }
    }
}

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
        columns = StaggeredGridCells.Adaptive(minSize = minCardHeight),
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
    ) {
        items(size) { index ->
            if (index >= size - 1 && !endReached && !isLoadingNextPage) {
                onFetchNextPage()
            }
            val hero = if (index <= heroes.size - 1) heroes[index] else null
            HeroCard(
                modifier = Modifier
                    .clickable {
                        hero?.let {
                            onHeroClicked(it.id.toString())
                        }
                    }
                    .fillMaxSize(),
                hero = hero,
            )
        }
        if (isLoadingNextPage) {
            item {
                HeroCard(
                    modifier = Modifier.fillMaxSize(),
                    hero = null,
                )
            }
        }
    }
}

@Composable
private fun HeroCard(
    modifier: Modifier,
    hero: Hero? = null,
) {
    Card(
        modifier = modifier
            .defaultMinSize(minHeight = minCardHeight)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        if (hero == null) {
            PlaceholderCardLoading()
        } else {
            val isLoading = remember { mutableStateOf(true) }
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(hero.thumbnailUrl)
                        .crossfade(true).scale(Scale.FILL).build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .heightIn(min = minCardHeight)
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = isLoading.value,
                            )
                        ),
                    onSuccess = {
                        isLoading.value = false
                    },
                )
                Text(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = hero.name,
                )
            }
        }
    }
}

@Composable
private fun PlaceholderCardLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(minCardHeight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
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

@Preview
@Composable
private fun HeroesListScreenPreview() {
    MhTheme {
        Surface {
            HeroesListScreen(uiState = HeroesListUiState(), onHeroClicked = {}) {}
        }
    }
}

private val minCardHeight = 128.dp
