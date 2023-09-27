package com.hlandim.marvelheroes.core.data.repository

import com.hlandim.marvelheroes.core.data.util.Resource
import com.hlandim.marvelheroes.core.data.util.mapper.toHero
import com.hlandim.marvelheroes.core.data.util.mapper.toHeroEntityList
import com.hlandim.marvelheroes.database.HeroDatabase
import com.hlandim.marvelheroes.model.Hero
import com.hlandim.marvelheroes.network.MarvelApi
import com.hlandim.marvelheroes.network.dto.ListResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Singleton
class HeroRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    db: HeroDatabase
) : HeroRepository {
    private val dao = db.dao

    override fun getHeroes(
        offset: Int,
        limit: Int,
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Hero>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localEntries = dao.searchHero(
                offset = offset,
                limit = limit,
                query = query
            )
            emit(
                Resource.Success(
                    data = localEntries.map { it.toHero() }
                )
            )

            val isDbEmpty = localEntries.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }
            val remoteEntries = try {
                api.getHeroes(
                    offset = offset,
                    limit = limit,
                    nameStartWith = query
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            }

            remoteEntries?.let { entries: ListResponseDto ->
                dao.clearHeroes(entries.data.results.map { it.id })
                dao.insertHeroes(entries.toHeroEntityList())
                emit(
                    Resource.Success(
                        data = dao.searchHero(
                            offset = offset,
                            limit = limit,
                            query = query
                        ).map { heroEntity -> heroEntity.toHero() }
                    )
                )
            }
            emit(Resource.Loading(isLoading = false))
        }
    }
}
