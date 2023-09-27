package com.hlandim.marvelheroes.core.data.repository

import com.hlandim.marvelheroes.core.data.util.Resource
import com.hlandim.marvelheroes.model.Hero
import kotlinx.coroutines.flow.Flow

/**
 * Created by Hugo Santos on 20/09/2023.
 */
interface HeroRepository {
    fun getHeroes(
        offset: Int,
        limit: Int,
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Hero>>>
}
