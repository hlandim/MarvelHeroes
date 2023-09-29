package com.hlandim.marvelheroes.core.data.di

import com.hlandim.marvelheroes.core.data.repository.HeroRepository
import com.hlandim.marvelheroes.core.data.repository.HeroRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bingHeroRepository(
        heroRepository: HeroRepositoryImpl
    ): HeroRepository
}
