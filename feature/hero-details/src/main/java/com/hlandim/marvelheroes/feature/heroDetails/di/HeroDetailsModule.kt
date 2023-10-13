package com.hlandim.marvelheroes.feature.heroDetails.di

import com.hlandim.marvelheroes.core.data.di.dataModule
import com.hlandim.marvelheroes.feature.heroDetails.HeroDetailsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val heroDetailsModule: Module = module {
    includes(dataModule)
    single { Dispatchers.IO }
    viewModel { HeroDetailsViewModel(savedStateHandle = get(), heroRepository = get()) }
}
