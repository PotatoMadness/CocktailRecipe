package com.potatomadness.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.potatomadness.database.model.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: IngredientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFullInfo(ingredient: IngredientEntity)

    @Query("SELECT * FROM Ingredient")
    fun getAll(): Flow<List<IngredientEntity>>
}