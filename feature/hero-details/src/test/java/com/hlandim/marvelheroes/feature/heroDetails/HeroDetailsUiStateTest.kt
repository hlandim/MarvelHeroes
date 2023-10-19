package com.hlandim.marvelheroes.feature.heroDetails

import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.model.HeroColors
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test

internal class HeroDetailsUiStateTest {

    @Test
    fun `when creating a UiState without hero colors it should generate the the colors`() {
        HeroDetailsUiState().colors shouldHaveSize 0
    }

    @Test
    fun `when creating a UiState with hero colors it should generate the the colors`() {
        HeroDetailsUiState(
            hero = Hero(
                id = 0,
                name = "",
                thumbnailUrl = "",
                heroColors = HeroColors(
                    vibrantColorRgb = 1,
                    darkVibrantColorRgb = 2,
                    lightMutedColorRgb = 2,
                    mutedColorRgb = 3,
                    darkMutedColorRgb = 4
                )
            )
        ).colors shouldHaveSize 5
    }
}
