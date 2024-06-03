package com.potatomadness.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.potatomadness.data.model.Cocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cocktail: Cocktail): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFullInfo(cocktail: Cocktail)

    @Update
    suspend fun update(cocktail: Cocktail)

    @Transaction
    suspend fun upsert(cocktail: Cocktail) {
        val id = insert(cocktail = cocktail)
        if(id == -1L && cocktail.isSimplySaved()) {
            update(cocktail)
        }
    }

    @Delete
    suspend fun delete(cocktail: Cocktail)

    @Query("SELECT EXISTS(SELECT 1 FROM Cocktail WHERE id = :cocktailId and isFavorite != 0 LIMIT 1)")
    fun isFavorite(cocktailId: Int): Flow<Boolean>

    @Query("SELECT * FROM Cocktail")
    fun getAll(): Flow<List<Cocktail>>

    @Query("SELECT * FROM Cocktail WHERE isCustom != 0")
    fun getMyRecipes(): Flow<List<Cocktail>>

    @Query("SELECT * FROM Cocktail WHERE isFavorite != 0")
    fun getFavoriteRecipes(): Flow<List<Cocktail>>

    @Query("SELECT EXISTS(SELECT 1 FROM Cocktail WHERE name = :name)")
    suspend fun isExist(name: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM Cocktail WHERE id = :id)")
    suspend fun isExist(id: Int): Boolean

    @Query("SELECT * FROM Cocktail WHERE id = :id")
    suspend fun getRecipeById(id: Int): Cocktail
}