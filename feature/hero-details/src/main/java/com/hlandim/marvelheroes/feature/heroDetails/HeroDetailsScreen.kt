package com.hlandim.marvelheroes.feature.heroDetails

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.hlandim.marvelheroes.core.ui.R
import com.hlandim.marvelheroes.ui.component.ErrorDialog
import com.hlandim.marvelheroes.ui.component.shimmerBrush
import org.koin.androidx.compose.koinViewModel

@Composable
fun HeroDetailsRoute(
    viewModel: HeroDetailsViewModel = koinViewModel(),
) {
    val uiState: HeroDetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    Surface(Modifier.fillMaxSize()) {
        HeroDetailsScreen(
            uiState = uiState,
            onThumbnailLoaded = remember {
                {
                    viewModel.onUiEvent(HeroDetailsUiEvent.OnHeroImageLoaded(it))
                }
            },
        )
    }
}

@Composable
private fun HeroDetailsScreen(
    uiState: HeroDetailsUiState,
    onThumbnailLoaded: (Bitmap) -> Unit,
) {
    val modifier = remember {
        mutableStateOf(
            Modifier
                .fillMaxSize()
        )
    }

    uiState.colors.let { heroColors ->
        if (heroColors.isNotEmpty()) {
            LaunchedEffect(key1 = Unit) {
                modifier.value = modifier.value.background(
                    brush = Brush.linearGradient(
                        colors = heroColors.subList(0, 2),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
            }
        }
    }
    Box(
        modifier = modifier.value,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )
        with(uiState) {
            HeroDetailsContent(this, onThumbnailLoaded)
            if (isLoading) {
                CircularProgressIndicator()
            }
            genericErrorMsg?.let {
                ErrorDialog(
                    message = it.asString(),
                    onDismissRequest = { /*TODO*/ },
                    onConfirmClicked = { /*TODO*/ },
                )
            }
        }
    }
}

@Composable
private fun HeroDetailsContent(
    uiState: HeroDetailsUiState,
    onThumbnailLoaded: (Bitmap) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        uiState.hero?.let {
            val isLoadingThumbnail = remember { mutableStateOf(true) }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(it.thumbnailUrl)
                    .crossfade(true).scale(Scale.FIT).allowHardware(true).build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = isLoadingThumbnail.value,
                        )
                    ),
                onSuccess = { success ->
                    isLoadingThumbnail.value = false
                    onThumbnailLoaded((success.result.drawable as BitmapDrawable).bitmap)
                },
                onError = {
                    isLoadingThumbnail.value = false
                },
                error = painterResource(id = R.drawable.not_found),
            )
        }
    }
    Text(text = uiState.hero?.name.orEmpty())
}
