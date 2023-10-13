package com.hlandim.marvelheroes.core.data.di

import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.core.data.repository.HeroRepositoryImpl
import com.hlandim.marvelheroes.database.di.databaseModule
import com.hlandim.marvelheroes.network.di.networkModule
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module = module {
    includes(networkModule)
    includes(databaseModule)
    single<HeroRepository> {
        HeroRepositoryImpl(
            api = get(),
            db = get(),
            networkCheck = get(),
        )
    }
}
