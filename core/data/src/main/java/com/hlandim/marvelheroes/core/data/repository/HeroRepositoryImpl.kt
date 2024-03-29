package com.hlandim.marvelheroes.core.data.repository

import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.core.data.util.mapper.toHero
import com.hlandim.marvelheroes.core.data.util.mapper.toHeroEntity
import com.hlandim.marvelheroes.core.data.util.mapper.toHeroEntityList
import com.hlandim.marvelheroes.database.MarvelHeroesDatabase
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.network.MarvelApi
import com.hlandim.marvelheroes.network.util.NetworkCheck
import javax.inject.Inject

/**
 * Created by Hugo Santos on 20/09/2023.
 */
class HeroRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    db: MarvelHeroesDatabase,
    networkCheck: NetworkCheck
) : BaseRepository(networkCheck), HeroRepository {
    private val dao = db.heroDao

    override suspend fun getHeroes(
        offset: Int,
        limit: Int,
        fetchFromRemote: Boolean,
        query: String
    ): DataResponse<List<Hero>> {
        val localEntries = dao.searchHeroes(
            offset = offset,
            limit = limit,
            query = query
        )

        val isDbEmpty = localEntries.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        if (shouldJustLoadFromCache) {
            return DataResponse.Success(
                data = localEntries.map { it.toHero() }
            )
        }
        val remoteEntries = handleApi {
            api.getHeroes(
                offset = offset,
                limit = limit,
                nameStartsWith = query.ifBlank { null },
            )
        }
        return when (remoteEntries) {
            is DataResponse.Error -> DataResponse.Error(
                dao.searchHeroes(
                    offset = offset,
                    limit = limit,
                    query = query
                ).map { heroEntity -> heroEntity.toHero() },
                remoteEntries.code,
                remoteEntries.message
            )

            is DataResponse.Exception -> DataResponse.Exception(remoteEntries.e)
            is DataResponse.Success -> {
                remoteEntries.data?.let { responseDto ->
                    dao.clearHeroes(responseDto.data.results.map { it.id })
                    dao.insertHeroes(responseDto.toHeroEntityList())
                }
                DataResponse.Success(
                    dao.searchHeroes(
                        offset = offset,
                        limit = limit,
                        query = query
                    ).map { heroEntity -> heroEntity.toHero() }
                )
            }
        }
    }

    override suspend fun getHero(id: Int): Hero {
        val heroDb = dao.searchHeroById(id)
        return heroDb.toHero()
    }

    override suspend fun updateHero(hero: Hero) {
        dao.updateHero(hero.toHeroEntity())
    }
}
