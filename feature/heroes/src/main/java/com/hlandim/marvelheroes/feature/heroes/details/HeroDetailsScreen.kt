package com.hlandim.marvelheroes.feature.heroes.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HeroDetailsRoute(
    viewModel: HeroDetailsViewModel = koinViewModel(),
) {
    val uiState: HeroDetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    Surface(Modifier.fillMaxSize()) {
        HeroDetailsScreen(uiState = uiState)
    }
}

@Composable
private fun HeroDetailsScreen(
    uiState: HeroDetailsUiState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = uiState.hero.name)
    }
}
