package com.hlandim.marvelheroes.core.data.util.mapper

import com.hlandim.marvelheroes.database.model.HeroEntity
import com.hlandim.marvelheroes.model.Hero

/**
 * Created by Hugo Santos on 20/09/2023.
 */

fun HeroEntity.toHero(): Hero =
    Hero(
        id = id,
        name = name,
        thumbnailUrl = thumbnailUrl,
    )

fun Hero.toHeroEntity(): HeroEntity = HeroEntity(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
)
