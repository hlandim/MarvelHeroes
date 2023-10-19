package com.hlandim.marvelheroes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hlandim.marvelheroes.database.dao.HeroDao
import com.hlandim.marvelheroes.database.model.HeroEntity

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Database(
    entities = [HeroEntity::class],
    version = 1
)
abstract class MarvelHeroesDatabase : RoomDatabase() {
    abstract val heroDao: HeroDao
}
