package com.hlandim.marvelheroes.network.dto

import com.squareup.moshi.Json

/**
 * Created by Hugo Santos on 20/09/2023.
 */
data class HeroDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "thumbnail")
    val thumbnail: ThumbnailDto
)
