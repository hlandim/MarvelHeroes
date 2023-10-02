package com.hlandim.marvelheroes.feature.heroes.di

import com.hlandim.marvelheroes.core.data.di.dataModule
import com.hlandim.marvelheroes.feature.heroes.details.HeroDetailsViewModel
import com.hlandim.marvelheroes.feature.heroes.list.HeroesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val heroesModule: Module = module {
    includes(dataModule)
    viewModel { HeroesListViewModel(heroRepository = get()) }
    viewModel { HeroDetailsViewModel(heroRepository = get()) }
}
