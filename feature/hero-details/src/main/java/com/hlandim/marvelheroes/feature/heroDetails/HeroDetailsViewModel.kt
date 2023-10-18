package com.hlandim.marvelheroes.feature.heroDetails

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.feature.heroDetails.navigation.HeroDetailsArgs
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HeroDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    val heroRepository: HeroRepository,
) : ViewModel() {

    private val heroDetailsArgs: HeroDetailsArgs = HeroDetailsArgs(savedStateHandle)

    private val _uiState: MutableStateFlow<HeroDetailsUiState> =
        MutableStateFlow(HeroDetailsUiState())

    val uiState: StateFlow<HeroDetailsUiState> = _uiState

    init {
        fetchHeroDetails()
    }

    fun onUiEvent(event: HeroDetailsUiEvent) {
        if (event is HeroDetailsUiEvent.OnHeroImageLoaded) {
            viewModelScope.launch {
                Palette.from(event.thumbnail.copy(Bitmap.Config.RGBA_F16, true))
                    .generate { palette ->
                        updateState { copy(paletteColors = palette) }
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
