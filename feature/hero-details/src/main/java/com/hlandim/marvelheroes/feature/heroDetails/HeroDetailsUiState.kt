package com.hlandim.marvelheroes.feature.heroDetails

import androidx.palette.graphics.Palette
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.util.UiText

data class HeroDetailsUiState(
    val hero: Hero? = null,
    val paletteColors: Palette? = null,
    val genericErrorMsg: UiText? = null,
    val isLoading: Boolean = true,
)
