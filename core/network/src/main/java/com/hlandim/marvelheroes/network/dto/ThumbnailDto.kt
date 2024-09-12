package com.hlandim.marvelheroes.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@JsonClass(generateAdapter = true)
data class ThumbnailDto(
    @Json(name = "path")
    val path: String,
    @Json(name = "extension")
    val extension: String
)
