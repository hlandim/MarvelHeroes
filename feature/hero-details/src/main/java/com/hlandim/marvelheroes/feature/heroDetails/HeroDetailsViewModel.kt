package com.hlandim.marvelheroes.feature.heroDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.feature.heroDetails.navigation.HeroDetailsArgs
import com.hlandim.marvelheroes.ui.util.UiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class HeroDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    val heroRepository: HeroRepository,
) : ViewModel() {

    private val heroDetailsArgs: HeroDetailsArgs = HeroDetailsArgs(savedStateHandle)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HeroDetailsUiState> =
        heroRepository.getHero(heroDetailsArgs.heroId.toInt())
            .flatMapLatest { resource ->
                flowOf(
                    when (resource) {
                        is DataResponse.Error -> HeroDetailsUiState.Error(
                            UiText.DynamicString(
                                resource.message
                            )
                        )

                        is DataResponse.Exception -> HeroDetailsUiState.Error(
                            UiText.DynamicString(
                                resource.e.message.orEmpty()
                            )
                        )

                        is DataResponse.Success -> HeroDetailsUiState.Found(resource.data!!)
                    }
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = HeroDetailsUiState.Loading,
            )
}
