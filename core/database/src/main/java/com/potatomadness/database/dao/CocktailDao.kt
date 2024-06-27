package com.potatomadness.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.potatomadness.database.model.CocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cocktail: CocktailEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFullInfo(cocktail: CocktailEntity)

    @Update
    suspend fun update(cocktail: CocktailEntity)

    @Transaction
    suspend fun upsert(cocktail: CocktailEntity) {
        val id = insert(cocktail = cocktail)
        if(id == -1L) {
            update(cocktail)
        }
    }

    @Query("UPDATE Cocktail SET isFavorite = :to WHERE id = :id")
    suspend fun updateFavorite(to: Boolean, id: Int)

    @Delete
    suspend fun delete(cocktail: CocktailEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM Cocktail WHERE id = :cocktailId and isFavorite != 0 LIMIT 1)")
    fun isFavorite(cocktailId: Int): Flow<Boolean>

    @Query("SELECT * FROM Cocktail")
    fun getAll(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM Cocktail WHERE isCustom != 0")
    fun getMyRecipes(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM Cocktail WHERE isFavorite != 0")
    fun getFavoriteRecipes(): Flow<List<CocktailEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM Cocktail WHERE name = :name)")
    suspend fun isExist(name: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM Cocktail WHERE id = :id)")
    suspend fun isExist(id: Int): Boolean

    @Query("SELECT * FROM Cocktail WHERE id = :id")
    suspend fun getRecipeById(id: Int): CocktailEntity

    @Query("SELECT id from Cocktail ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Int
}