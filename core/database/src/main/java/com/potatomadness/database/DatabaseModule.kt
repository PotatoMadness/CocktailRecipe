package com.potatomadness.database

import android.content.Context
import com.potatomadness.database.dao.CocktailDao
import com.potatomadness.database.dao.IngredientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideCocktailDao(appDatabase: AppDatabase): CocktailDao {
        return appDatabase.cocktailDao()
    }

    @Provides
    fun provideIngredientDao(appDatabase: AppDatabase): IngredientDao {
        return appDatabase.ingredientDao()
    }
}