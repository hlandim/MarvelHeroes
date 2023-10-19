package com.hlandim.marvelheroes.database.di

import androidx.room.Room
import com.hlandim.marvelheroes.database.MarvelHeroesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single<MarvelHeroesDatabase> {
        Room.databaseBuilder(
            androidContext(),
            MarvelHeroesDatabase::class.java,
            "herodb.db"
        ).build()
    }
}
