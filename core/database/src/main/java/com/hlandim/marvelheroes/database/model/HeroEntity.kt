package com.hlandim.marvelheroes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Entity(tableName = "hero")
data class HeroEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val thumbnailUrl: String,
)
