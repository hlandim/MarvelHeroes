package com.hlandim.marvelheroes.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
                        .shimmerLoadingAnimation(),
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
                .shimmerLoadingAnimation()
        )
        Text(
            text = "",
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .shimmerLoadingAnimation()
        )
    }
}

fun Modifier.shimmerLoadingAnimation(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {
        val shimmerColors = listOf(
            Color.Gray.copy(alpha = 0.3f),
            Color.Gray.copy(alpha = 0.5f),
            Color.Gray.copy(alpha = 1.0f),
            Color.Gray.copy(alpha = 0.5f),
            Color.Gray.copy(alpha = 0.3f),
        )

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
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
