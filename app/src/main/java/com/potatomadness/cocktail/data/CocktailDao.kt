package com.potatomadness.cocktail.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktail: Cocktail)

    @Query("SELECT EXISTS(SELECT 1 FROM Cocktail WHERE id = :cocktailId LIMIT 1)")
    fun isSaved(cocktailId: String): Flow<Boolean>

    @Query("SELECT * FROM Cocktail")
    fun getAll(): Flow<List<Cocktail>>
}