package com.potatomadness.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: com.potatomadness.data.model.Ingredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFullInfo(ingredient: com.potatomadness.data.model.Ingredient)

    @Query("SELECT * FROM Ingredient")
    fun getAll(): Flow<List<com.potatomadness.data.model.Ingredient>>
}