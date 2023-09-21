package com.hlandim.marvelheroes.data.remote

import com.hlandim.marvelheroes.data.remote.dto.ListResponseDto
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
        @Query("nameStartWith") nameStartWith: String,
    ): ListResponseDto

    companion object {
        const val BASE_URL: String = "http://gateway.marvel.com/v1/public/"
    }
}