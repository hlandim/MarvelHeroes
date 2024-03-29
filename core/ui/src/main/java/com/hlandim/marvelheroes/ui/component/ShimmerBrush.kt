package com.hlandim.marvelheroes.ui.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private const val ANIMATION_LABEL = "SHIMMER_ANIMATION"

@Suppress("MagicNumber")
@Composable
fun shimmerBrush(
    showShimmer: Boolean = true,
    targetValue: Float = 1000f,
    color: Color = MaterialTheme.colorScheme.onPrimary
): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            color.copy(alpha = 0.6f),
            color.copy(alpha = 0.2f),
            color.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(ANIMATION_LABEL)
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(500),
                repeatMode = RepeatMode.Reverse,
            ),
            label = ANIMATION_LABEL
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}
