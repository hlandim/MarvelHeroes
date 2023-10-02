package com.hlandim.marvelheroes.di

import com.hlandim.marvelheroes.feature.heroes.di.heroesModule
import org.koin.core.module.Module
import org.koin.dsl.module

val mhModule: Module = module {
    includes(heroesModule)
}
