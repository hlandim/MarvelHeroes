package com.hlandim.marvelheroes.data.remote.dto.mapper

import com.hlandim.marvelheroes.data.local.HeroEntity
import com.hlandim.marvelheroes.data.remote.dto.ListResponseDto

/**
 * Created by Hugo Santos on 20/09/2023.
 */

fun ListResponseDto.toHeroEntityList(): List<HeroEntity> = data.results.map {
    HeroEntity(
        id = it.id,
        name = it.name,
        thumbnailPath = it.thumbnail.path,
        thumbnailExtension = it.thumbnail.extension,
    )
}