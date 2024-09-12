package com.hlandim.marvelheroes.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@JsonClass(generateAdapter = true)
data class HeroDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "thumbnail")
    val thumbnail: ThumbnailDto
)
