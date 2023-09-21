package com.hlandim.marvelheroes.data.remote.dto

import com.squareup.moshi.Json

/**
 * Created by Hugo Santos on 20/09/2023.
 */
data class ThumbnailDto(
    @Json(name = "path")
    val path: String,
    @Json(name = "extension")
    val extension: String,
)
