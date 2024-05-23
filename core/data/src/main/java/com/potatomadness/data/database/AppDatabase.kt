package com.potatomadness.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [com.potatomadness.data.model.Cocktail::class, com.potatomadness.data.model.Ingredient::class], version = 8)
@TypeConverters(com.potatomadness.data.util.RecipeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cocktailDao(): com.potatomadness.data.dao.CocktailDao
    abstract fun ingredientDao(): com.potatomadness.data.dao.IngredientDao

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