package com.hlandim.marvelheroes.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.hlandim.marvelheroes.core.ui.R
import com.hlandim.marvelheroes.ui.theme.MhTheme
import com.hlandim.marvelheroes.ui.util.LightDarkPreview

@Composable
fun MhGridCard(
    modifier: Modifier = Modifier,
    thumbnailUrl: String? = null,
    title: String? = null,
    isPlaceholder: Boolean = false,
) {
    Card(
        modifier = modifier
            .defaultMinSize(minHeight = minMhCardHeight),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        if (isPlaceholder) {
            PlaceholderCardLoading(
                Modifier
                    .fillMaxWidth()
                    .height(minMhCardHeight)
            )
        } else {
            val isLoadingThumbnail = remember { mutableStateOf(true) }
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(thumbnailUrl)
                        .crossfade(true).scale(Scale.FILL).build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .defaultMinSize(minHeight = minMhCardHeight)
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = isLoadingThumbnail.value,
                            )
                        ),
                    onSuccess = {
                        isLoadingThumbnail.value = false
                    },
                    onError = {
                        isLoadingThumbnail.value = false
                    },
                    error = painterResource(id = R.drawable.not_found),
                )
                Text(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = title.orEmpty(),
                )
            }
        }
    }
}

@Composable
private fun PlaceholderCardLoading(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = true,
                    )
                )
        )
        Text(
            text = "",
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .background(
                    shimmerBrush(
                        targetValue = 1500f,
                        showShimmer = true,
                    )
                )
        )
    }
}

@LightDarkPreview
@Composable
private fun Preview() {
    MhTheme {
        Surface {
            Box(Modifier.padding(20.dp)) {
                MhGridCard(isPlaceholder = true)
            }
        }
    }
}

val minMhCardHeight = 128.dp
