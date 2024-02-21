package com.hlandim.marvelheroes.feature.heroesList.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroesListModule {

    @Provides
    @Singleton
    fun provideDispatcher() = Dispatchers.IO
}
