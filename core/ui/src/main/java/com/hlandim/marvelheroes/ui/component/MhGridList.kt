package com.hlandim.marvelheroes.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hlandim.marvelheroes.ui.theme.MhTheme
import com.hlandim.marvelheroes.ui.util.LightDarkPreview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

// Used to create the initial list items placeholders.
private const val DEFAULT_SIZE: Int = 20

@Composable
@Suppress("LongParameterList")
fun MhGridList(
    modifier: Modifier = Modifier,
    list: ImmutableList<GridListData>,
    onItemClick: (String) -> Unit,
    onFetchNextPage: () -> Unit,
    endReached: Boolean,
    isLoadingNextPage: Boolean,
) {
    val size = if (list.isNotEmpty()) list.size else DEFAULT_SIZE
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = minMhCardHeight),
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp,
    ) {
        items(size, key = { it }) { index ->
            if (index >= size - 1 && !endReached && !isLoadingNextPage) {
                onFetchNextPage()
            }
            val itemData = if (index <= list.size - 1) list[index] else null
            MhGridCard(
                modifier = Modifier
                    .animateItem()
                    .clickable {
                        itemData?.let {
                            onItemClick(it.id.toString())
                        }
                    }
                    .fillMaxSize(),
                thumbnailUrl = itemData?.thumbnailUrl,
                title = itemData?.label.orEmpty(),
                isPlaceholder = itemData == null,
            )
        }
        if (isLoadingNextPage) {
            item {
                MhGridCard(
                    modifier = Modifier
                        .animateItem()
                        .fillMaxSize(),
                    isPlaceholder = true,
                )
            }
        }
    }
}

@LightDarkPreview
@Composable
@Suppress("MagicNumber")
private fun MhGridPreview() {
    MhTheme {
        Surface {
            MhGridList(
                modifier = Modifier.fillMaxSize(),
                list = List(20) { index ->
                    GridListData(
                        id = index,
                        label = "Hero ${index + 1}",
                        thumbnailUrl = "",
                    )
                }.toPersistentList(),
                onItemClick = {},
                onFetchNextPage = {},
                endReached = false,
                isLoadingNextPage = false

            )
        }
    }
}

data class GridListData(
    val id: Int,
    val label: String,
    val thumbnailUrl: String,
)
