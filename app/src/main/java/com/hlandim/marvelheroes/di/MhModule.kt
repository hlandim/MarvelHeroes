package com.hlandim.marvelheroes.di

import com.hlandim.marvelheroes.feature.heroDetails.di.heroDetailsModule
import com.hlandim.marvelheroes.feature.heroesList.di.heroesListModule
import org.koin.core.module.Module
import org.koin.dsl.module

val mhModule: Module = module {
    includes(heroesListModule)
    includes(heroDetailsModule)
}
