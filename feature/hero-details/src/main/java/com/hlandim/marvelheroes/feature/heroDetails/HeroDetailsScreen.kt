package com.hlandim.marvelheroes.feature.heroDetails

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.hlandim.marvelheroes.core.ui.R
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.model.HeroColors
import com.hlandim.marvelheroes.ui.component.ErrorDialog
import com.hlandim.marvelheroes.ui.component.shimmerBrush
import com.hlandim.marvelheroes.ui.theme.MhTheme
import com.hlandim.marvelheroes.ui.util.LightDarkPreview
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
            Modifier.fillMaxSize()
        )
    }
    // Setting up the dynamic background colors
    uiState.colors.let { heroColors ->
        if (heroColors.size >= 2) {
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
        HeroImage(
            Modifier
                .align(Alignment.TopCenter)
                .height(heroImageHeight),
            uiState,
            onThumbnailLoaded,
        )
        HeroDetailsContent(uiState)
    }
}

@Composable
private fun HeroDetailsContent(uiState: HeroDetailsUiState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        with(uiState) {
            HeroDetailsCard(this)
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
private fun HeroImage(
    modifier: Modifier,
    uiState: HeroDetailsUiState,
    onThumbnailLoaded: (Bitmap) -> Unit,
) {
    var imageVisible by remember { mutableStateOf(false) }
    val imageAlpha: Float by animateFloatAsState(
        targetValue = if (imageVisible) 0.5f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing,
        ),
        label = "",
    )
    Card(
        modifier = modifier.alpha(imageAlpha),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        uiState.hero?.let {
            val isLoadingThumbnail = remember { mutableStateOf(true) }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(it.thumbnailUrl)
                    .crossfade(true).scale(Scale.FILL).allowHardware(true).build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = isLoadingThumbnail.value,
                        )
                    ),
                onSuccess = { success ->
                    isLoadingThumbnail.value = false
                    onThumbnailLoaded((success.result.drawable as BitmapDrawable).bitmap)
                    imageVisible = true
                },
                onError = {
                    isLoadingThumbnail.value = false
                },
                error = painterResource(id = R.drawable.not_found),
            )
        }
    }
}

@Composable
@Suppress("MagicNumber")
private fun HeroDetailsCard(
    uiState: HeroDetailsUiState,
) {
    val modifier = remember {
        mutableStateOf(
            Modifier.fillMaxWidth()
        )
    }

    var textColor = MaterialTheme.colorScheme.onSecondaryContainer
    uiState.hero?.heroColors?.let {
        val isDarKMode = isSystemInDarkTheme()
        LaunchedEffect(key1 = isDarKMode) {
            val (backgroundColor, textColorTmp) = if (isDarKMode) {
                Pair(it.darkMutedColorRgb, it.lightMutedColorRgb)
            } else {
                Pair(it.lightMutedColorRgb, it.darkMutedColorRgb)
            }
            textColorTmp?.let { colorRgb ->
                textColor = Color(colorRgb)
            }
            backgroundColor?.let { colorRgb ->
                modifier.value = modifier.value.background(color = Color(colorRgb))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(heroImageHeight - (heroImageHeight.div(2.5f))))
        HeroInfoCard(modifier, uiState, textColor)
        Spacer(modifier = Modifier.height(24.dp))
        SeriesCard(modifier, uiState, textColor)
        Spacer(modifier = Modifier.height(24.dp))
        EventsCard(modifier, uiState, textColor)
        Spacer(modifier = Modifier.height(24.dp))
        ComicsCard(modifier, uiState, textColor)
    }
}

@Composable
private fun HeroInfoCard(
    modifier: MutableState<Modifier>,
    uiState: HeroDetailsUiState,
    textColor: Color
) {
    Card(
        elevation = CardDefaults.cardElevation(heroDetailsCardElevation)
    ) {
        Column(modifier = modifier.value.padding(12.dp)) {
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
        }
    }
}

@Composable
private fun SeriesCard(
    modifier: MutableState<Modifier>,
    uiState: HeroDetailsUiState,
    textColor: Color
) {
    Card(
        elevation = CardDefaults.cardElevation(heroDetailsCardElevation)
    ) {
        Column(modifier = modifier.value.padding(12.dp)) {
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
        }
    }
}

@Composable
private fun EventsCard(
    modifier: MutableState<Modifier>,
    uiState: HeroDetailsUiState,
    textColor: Color
) {
    Card(
        elevation = CardDefaults.cardElevation(heroDetailsCardElevation)
    ) {
        Column(modifier = modifier.value.padding(12.dp)) {
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
        }
    }
}

@Composable
private fun ComicsCard(
    modifier: MutableState<Modifier>,
    uiState: HeroDetailsUiState,
    textColor: Color
) {
    Card(
        elevation = CardDefaults.cardElevation(heroDetailsCardElevation)
    ) {
        Column(modifier = modifier.value.padding(12.dp)) {
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
            Text(text = uiState.hero?.name.orEmpty(), color = textColor)
        }
    }
}

@LightDarkPreview
@Composable
private fun Preview() {
    MhTheme {
        Surface {
            HeroDetailsScreen(
                uiState = HeroDetailsUiState(
                    hero = Hero(
                        id = 1596,
                        name = "Dylan Golden",
                        thumbnailUrl = "https://www.google.com/#q=sumo",
                        heroColors = HeroColors(
                            vibrantColorRgb = null,
                            darkVibrantColorRgb = null,
                            lightMutedColorRgb = null,
                            mutedColorRgb = null,
                            darkMutedColorRgb = null
                        )
                    ),
                    genericErrorMsg = null,
                    isLoading = false
                ),
                onThumbnailLoaded = {}
            )
        }
    }
}

private val heroImageHeight = 300.dp
private val heroDetailsCardElevation = 10.dp
