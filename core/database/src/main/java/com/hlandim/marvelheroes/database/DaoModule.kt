package com.hlandim.marvelheroes.database

import com.hlandim.marvelheroes.database.dao.HeroDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesHereDao(
        database: HeroDatabase
    ): HeroDao = database.dao
}
