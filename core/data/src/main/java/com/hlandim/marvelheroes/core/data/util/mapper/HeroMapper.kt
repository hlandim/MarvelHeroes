package com.hlandim.marvelheroes.core.data.util.mapper

import com.hlandim.marvelheroes.database.model.HeroEntity
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.model.HeroColors

/**
 * Created by Hugo Santos on 20/09/2023.
 */

fun HeroEntity.toHero(): Hero =
    Hero(
        id = id,
        name = name,
        thumbnailUrl = thumbnailUrl,
        heroColors = if (isColorsNotNull) {
            HeroColors(
                vibrantColorRgb = vibrantColorRgb,
                darkVibrantColorRgb = darkVibrantColorRgb,
                lightMutedColorRgb = lightMutedColorRgb,
                mutedColorRgb = mutedColorRgb,
                darkMutedColorRgb = darkMutedColorRgb
            )
        } else {
            null
        }
    )

fun Hero.toHeroEntity(): HeroEntity = HeroEntity(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    vibrantColorRgb = heroColors?.vibrantColorRgb,
    darkVibrantColorRgb = heroColors?.darkVibrantColorRgb,
    lightMutedColorRgb = heroColors?.lightMutedColorRgb,
    mutedColorRgb = heroColors?.mutedColorRgb,
    darkMutedColorRgb = heroColors?.darkMutedColorRgb
)
