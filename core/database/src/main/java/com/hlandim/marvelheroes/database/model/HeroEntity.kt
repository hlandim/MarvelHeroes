package com.hlandim.marvelheroes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Entity(tableName = "hero")
data class HeroEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val thumbnailUrl: String,
)
