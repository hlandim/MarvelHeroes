package com.hlandim.marvelheroes.feature.heroDetails

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
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
                    .crossfade(true).scale(Scale.FILL).allowHardware(true).build(),
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

        uiState.paletteColors?.let { palette ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                palette.vibrantSwatch?.let { BoxColor(it, "vibrantSwatch") }
                palette.darkVibrantSwatch?.let { BoxColor(it, "darkVibrantSwatch") }
                palette.lightMutedSwatch?.let { BoxColor(it, "lightMutedSwatch") }
                palette.mutedSwatch?.let { BoxColor(it, "mutedSwatch") }
                palette.darkMutedSwatch?.let { BoxColor(it, "darkMutedSwatch") }
            }
        }
    }
    Text(text = uiState.hero?.name.orEmpty())
}

@Composable
private fun BoxColor(
    swatch: Palette.Swatch,
    name: String,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = Color(swatch.rgb))
    ) {
        Text(
            modifier = Modifier.padding(15.dp),
            text = name,
        )
    }
}
