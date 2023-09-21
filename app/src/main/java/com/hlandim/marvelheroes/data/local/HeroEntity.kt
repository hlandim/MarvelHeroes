package com.hlandim.marvelheroes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Entity(tableName = "hero")
data class HeroEntity(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val thumbnailPath: String,
    val thumbnailExtension: String,
)
