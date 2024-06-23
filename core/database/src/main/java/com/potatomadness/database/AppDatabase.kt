package com.potatomadness.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.potatomadness.database.dao.CocktailDao
import com.potatomadness.database.dao.IngredientDao
import com.potatomadness.database.model.CocktailEntity
import com.potatomadness.database.model.IngredientEntity
import com.potatomadness.database.util.RecipeConverter

@Database(entities = [CocktailEntity::class, IngredientEntity::class], version = 8)
@TypeConverters(RecipeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun ingredientDao(): IngredientDao

    companion object {
        private const val DATABASE_NAME = "cocktail_recipe"

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {

            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}