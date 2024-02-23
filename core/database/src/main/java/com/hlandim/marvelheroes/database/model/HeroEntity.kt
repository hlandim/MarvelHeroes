package com.hlandim.marvelheroes.database.model

import androidx.room.Entity
import androidx.room.Ignore
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
    val vibrantColorRgb: Int? = null,
    val darkVibrantColorRgb: Int? = null,
    val lightMutedColorRgb: Int? = null,
    val mutedColorRgb: Int? = null,
    val darkMutedColorRgb: Int? = null,
) {
    @Ignore
    val isColorsNotNull = listOfNotNull(
        vibrantColorRgb,
        darkVibrantColorRgb,
        lightMutedColorRgb,
        mutedColorRgb,
        darkMutedColorRgb
    ).any()
}
