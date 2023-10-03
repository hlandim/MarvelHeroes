package com.hlandim.marvelheroes.feature.heroes.di

import com.hlandim.marvelheroes.core.data.di.dataModule
import com.hlandim.marvelheroes.feature.heroes.details.HeroDetailsViewModel
import com.hlandim.marvelheroes.feature.heroes.list.HeroesListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val heroesModule: Module = module {
    includes(dataModule)
    single { Dispatchers.IO }
    viewModel { HeroesListViewModel(heroRepository = get(), dispatcher = get()) }
    viewModel { HeroDetailsViewModel(heroRepository = get()) }
}
