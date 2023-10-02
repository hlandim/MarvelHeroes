package com.hlandim.marvelheroes.network

import com.hlandim.marvelheroes.network.dto.ListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Hugo Santos on 20/09/2023.
 */
interface MarvelApi {
    @GET("characters")
    suspend fun getHeroes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("nameStartsWith") nameStartsWith: String? = null,
    ): ListResponseDto

    companion object {
        const val BASE_URL: String = "http://gateway.marvel.com/v1/public/"
    }
}
