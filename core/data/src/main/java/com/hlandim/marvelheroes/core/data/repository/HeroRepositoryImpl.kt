package com.hlandim.marvelheroes.core.data.repository

import com.hlandim.marvelheroes.core.data.util.DataResponse
import com.hlandim.marvelheroes.core.data.util.mapper.toHero
import com.hlandim.marvelheroes.core.data.util.mapper.toHeroEntityList
import com.hlandim.marvelheroes.database.HeroDatabase
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.network.MarvelApi
import com.hlandim.marvelheroes.network.util.NetworkCheck
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Hugo Santos on 20/09/2023.
 */
class HeroRepositoryImpl(
    private val api: MarvelApi,
    db: HeroDatabase,
    networkCheck: NetworkCheck
) : BaseRepository(networkCheck), HeroRepository {
    private val dao = db.dao

    override fun getHeroes(
        offset: Int,
        limit: Int,
        fetchFromRemote: Boolean,
        query: String
    ): Flow<DataResponse<List<Hero>>> {
        return flow {
            emit(DataResponse.Loading())
            val localEntries = dao.searchHero(
                offset = offset,
                limit = limit,
                query = query
            )
            emit(
                DataResponse.Success(
                    data = localEntries.map { it.toHero() }
                )
            )

            val isDbEmpty = localEntries.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                return@flow
            }
            handleApiCall(
                apiCallWork = {
                    api.getHeroes(
                        offset = offset,
                        limit = limit,
                        nameStartsWith = query.ifBlank { null }
                    )
                },
                onSuccess = { remoteEntries ->
                    if (remoteEntries == null) {
                        emit(DataResponse.Error(message = "No heroes found"))
                    } else {
                        dao.clearHeroes(remoteEntries.data.results.map { it.id })
                        dao.insertHeroes(remoteEntries.toHeroEntityList())
                        emit(
                            DataResponse.Success(
                                data = dao.searchHero(
                                    offset = offset,
                                    limit = limit,
                                    query = query
                                ).map { heroEntity -> heroEntity.toHero() }
                            )
                        )
                    }
                },
            )
        }
    }

    override fun getHero(id: Int): Flow<DataResponse<Hero>> {
        return flow {
            emit(DataResponse.Loading())
            val heroDb = dao.searchHeroById(id)
            emit(DataResponse.Success(data = heroDb.toHero()))
        }
    }
}
