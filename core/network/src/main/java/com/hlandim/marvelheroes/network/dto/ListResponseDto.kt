package com.hlandim.marvelheroes.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@JsonClass(generateAdapter = true)
data class ListResponseDto(
    @Json(name = "data")
    val data: DataResponse
)

@JsonClass(generateAdapter = true)
data class DataResponse(
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "limit")
    val limit: Int,
    @Json(name = "total")
    val total: Int,
    @Json(name = "count")
    val count: Int,
    @Json(name = "results")
    val results: List<HeroDto>
)
