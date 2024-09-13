package com.hlandim.marvelheroes.feature.heroesList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hlandim.marvelheroes.ui.component.ErrorDialog
import com.hlandim.marvelheroes.ui.component.GridListData
import com.hlandim.marvelheroes.ui.component.MhGridList
import com.hlandim.marvelheroes.ui.theme.MhTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HeroesListScreen(
    viewModel: HeroesListViewModel = hiltViewModel(),
    onHeroClicked: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Surface(Modifier.fillMaxSize()) {
        HeroesListContent(
            uiState = uiState,
            onHeroClicked = remember { onHeroClicked },
            onErrorDialogDismissed = remember { { viewModel.onUiEvent(HeroesListUiEvent.OnErrorDismissed) } },
            onFetchNextPage = remember { { viewModel.onUiEvent(HeroesListUiEvent.FetchNextListPage) } },
        )
    }
}

@Composable
private fun HeroesListContent(
    uiState: HeroesListUiState,
    onHeroClicked: (String) -> Unit,
    onErrorDialogDismissed: () -> Unit,
    onFetchNextPage: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        MhGridList(
            list = uiState.uiList,
            onItemClick = onHeroClicked,
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

@Preview
@Composable
private fun HeroesListScreenPreview() {
    MhTheme {
        Surface {
            HeroesListContent(
                uiState = HeroesListUiState(
                    uiList = persistentListOf(
                        GridListData(
                            id = 1771,
                            label = "Osvaldo Morton",
                            thumbnailUrl = "",
                        ),
                        GridListData(
                            id = 9300,
                            label = "Zachary Pruitt",
                            thumbnailUrl = "",
                        ),
                        GridListData(
                            id = 3167,
                            label = "Dionne Soto",
                            thumbnailUrl = "",
                        ),
                        GridListData(
                            id = 4280,
                            label = "Tisha Mueller",
                            thumbnailUrl = "",
                        ),
                        GridListData(
                            id = 7884,
                            label = "Keisha McKnight",
                            thumbnailUrl = "",
                        ),
                        GridListData(
                            id = 2230,
                            label = "Misty Carroll",
                            thumbnailUrl = "",
                        ),
                        GridListData(
                            id = 8298,
                            label = "Vera Hull",
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
