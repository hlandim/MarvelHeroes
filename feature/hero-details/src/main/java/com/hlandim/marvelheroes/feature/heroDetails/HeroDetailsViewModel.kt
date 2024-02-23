package com.hlandim.marvelheroes.feature.heroDetails

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.feature.heroDetails.navigation.HeroDetailsArgs
import com.hlandim.marvelheroes.model.HeroColors
import com.hlandim.marvelheroes.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val heroRepository: HeroRepository,
) : ViewModel() {

    private val heroDetailsArgs: HeroDetailsArgs = HeroDetailsArgs(savedStateHandle)

    private val _uiState: MutableStateFlow<HeroDetailsUiState> =
        MutableStateFlow(HeroDetailsUiState())

    val uiState: StateFlow<HeroDetailsUiState> = _uiState

    init {
        fetchHeroDetails()
    }

    fun onUiEvent(event: HeroDetailsUiEvent) {
        if (event is HeroDetailsUiEvent.OnHeroImageLoaded && _uiState.value.hero?.heroColors == null) {
            Palette.from(event.thumbnail.copy(Bitmap.Config.RGBA_F16, true))
                .generate { palette ->
                    _uiState.value.hero?.let {
                        val updatedHero = it.copy(
                            heroColors = HeroColors(
                                vibrantColorRgb = palette?.vibrantSwatch?.rgb,
                                darkVibrantColorRgb = palette?.darkVibrantSwatch?.rgb,
                                lightMutedColorRgb = palette?.lightMutedSwatch?.rgb,
                                mutedColorRgb = palette?.mutedSwatch?.rgb,
                                darkMutedColorRgb = palette?.darkMutedSwatch?.rgb
                            )
                        )
                        viewModelScope.launch {
                            heroRepository.updateHero(updatedHero)
                            updateState { copy(hero = updatedHero) }
                        }
                    }
                }
        }
    }

    private fun fetchHeroDetails() {
        viewModelScope.launch {
            runCatching {
                val hero = heroRepository.getHero(heroDetailsArgs.heroId.toInt())
                updateState { copy(hero = hero, isLoading = false) }
            }.onFailure {
                updateState {
                    copy(
                        genericErrorMsg = UiText.DynamicString(it.message.orEmpty()),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun updateState(update: HeroDetailsUiState.() -> HeroDetailsUiState) {
        _uiState.update { update(it) }
    }
}
