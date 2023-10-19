package com.hlandim.marvelheroes.core.data.repository

import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.model.Hero

/**
 * Created by Hugo Santos on 20/09/2023.
 */
interface HeroRepository {
    suspend fun getHeroes(
        offset: Int,
        limit: Int,
        fetchFromRemote: Boolean,
        query: String
    ): DataResponse<List<Hero>>

    suspend fun getHero(id: Int): Hero

    suspend fun updateHero(hero: Hero)
}
