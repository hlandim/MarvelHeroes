package com.hlandim.marvelheroes.core.data.util.mapper

import com.hlandim.marvelheroes.database.model.HeroEntity
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.model.Thumbnail

/**
 * Created by Hugo Santos on 20/09/2023.
 */

fun HeroEntity.toHero(): Hero =
    Hero(
        name = name,
        thumbnail = Thumbnail(
            path = thumbnailPath,
            extension = thumbnailExtension
        )
    )

fun Hero.toHeroEntity(): HeroEntity = HeroEntity(
    name = name,
    thumbnailPath = thumbnail.path,
    thumbnailExtension = thumbnail.extension
)
