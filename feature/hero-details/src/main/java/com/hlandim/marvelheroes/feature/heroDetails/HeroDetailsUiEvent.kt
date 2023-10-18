package com.hlandim.marvelheroes.feature.heroDetails

import android.graphics.Bitmap

sealed interface HeroDetailsUiEvent {
    data class OnHeroImageLoaded(val thumbnail: Bitmap) : HeroDetailsUiEvent
}
