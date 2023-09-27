package com.hlandim.marvelheroes.network.dto

import com.squareup.moshi.Json

/**
 * Created by Hugo Santos on 20/09/2023.
 */
data class ListResponseDto(
    @Json(name = "data")
    val data: DataResponse
)

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
