package com.hlandim.marvelheroes.feature.heroDetails

import androidx.compose.ui.graphics.Color
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

data class HeroDetailsUiState(
    val hero: Hero? = null,
    val genericErrorMsg: UiText? = null,
    val isLoading: Boolean = true,
) {
    val colors: ImmutableList<Color> = mutableListOf<Color>().apply {
        with(hero?.heroColors) {
            this?.lightMutedColorRgb?.let { add(Color(it)) }
            this?.darkMutedColorRgb?.let { add(Color(it)) }
            this?.mutedColorRgb?.let { add(Color(it)) }
            this?.vibrantColorRgb?.let { add(Color(it)) }
            this?.darkVibrantColorRgb?.let { add(Color(it)) }
        }
    }.toPersistentList()
}
