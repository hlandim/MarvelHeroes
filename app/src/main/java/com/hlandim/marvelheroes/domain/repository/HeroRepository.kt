package com.hlandim.marvelheroes.domain.repository

import com.hlandim.marvelheroes.domain.model.Hero
import com.hlandim.marvelheroes.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Hugo Santos on 20/09/2023.
 */
interface HeroRepository {
    fun getHeroes(
        offset: Int,
        limit: Int,
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<Hero>>>
}