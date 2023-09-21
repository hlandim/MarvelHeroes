package com.hlandim.marvelheroes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Database(
    entities = [HeroEntity::class],
    version = 1,
)
abstract class HeroDatabase : RoomDatabase() {
    abstract val dao: HeroDao
}