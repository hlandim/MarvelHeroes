package com.hlandim.marvelheroes.core.data.util.mapper

import com.hlandim.marvelheroes.database.model.HeroEntity
import com.hlandim.marvelheroes.network.dto.ListResponseDto

/**
 * Created by Hugo Santos on 20/09/2023.
 */

fun ListResponseDto.toHeroEntityList(): List<HeroEntity> = data.results.map {
    HeroEntity(
        name = it.name,
        thumbnailUrl = with(it.thumbnail) { "$path.$extension" },
    )
}
