package com.hlandim.marvelheroes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hlandim.marvelheroes.database.model.HeroEntity

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Dao
interface HeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(
        heroes: List<HeroEntity>
    )

    @Query("DELETE FROM hero")
    suspend fun clearHeroes()

    @Update
    suspend fun updateHero(heroEntity: HeroEntity)

    @Query("DELETE FROM hero where id in (:ids)")
    suspend fun clearHeroes(ids: List<Int>)

    @Query(
        """
             SELECT *
             FROM hero
             WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' 
             LIMIT :limit 
             OFFSET :offset
        """
    )
    suspend fun searchHeroes(
        offset: Int,
        limit: Int,
        query: String
    ): List<HeroEntity>

    @Query("SELECT * FROM hero WHERE id = :id")
    suspend fun searchHeroById(
        id: Int,
    ): HeroEntity
}
