package com.hlandim.marvelheroes.model

/**
 * Created by Hugo Santos on 20/09/2023.
 */
data class Hero(
    val id: Int,
    val name: String,
    val thumbnailUrl: String,
    val heroColors: HeroColors? = null,
)

data class HeroColors(
    val vibrantColorRgb: Int? = null,
    val darkVibrantColorRgb: Int? = null,
    val lightMutedColorRgb: Int? = null,
    val mutedColorRgb: Int? = null,
    val darkMutedColorRgb: Int? = null,
)
