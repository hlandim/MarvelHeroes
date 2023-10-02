package com.hlandim.marvelheroes.feature.heroes.details

import androidx.lifecycle.ViewModel
import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.model.Thumbnail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HeroDetailsViewModel @Inject constructor(
    val heroRepository: HeroRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HeroDetailsUiState> = MutableStateFlow(
        HeroDetailsUiState(
            Hero(
                name = "Gretchen Leblanc",
                thumbnail = Thumbnail(
                    path = "arcu",
                    extension = "labores"
                )
            )
        )
    )
    val uiState: StateFlow<HeroDetailsUiState> = _uiState
}
