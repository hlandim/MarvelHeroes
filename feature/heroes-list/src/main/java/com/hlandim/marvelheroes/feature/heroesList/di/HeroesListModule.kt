package com.hlandim.marvelheroes.feature.heroesList.di

import com.hlandim.marvelheroes.core.data.di.dataModule
import com.hlandim.marvelheroes.feature.heroesList.HeroesListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val heroesListModule: Module = module {
    includes(dataModule)
    single { Dispatchers.IO }
    viewModel {
        HeroesListViewModel(
            heroRepository = get(),
            dispatcher = get()
        )
    }
}
