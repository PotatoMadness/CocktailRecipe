package com.potatomadness.cocktail.di

import com.potatomadness.cocktail.CocktailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCocktailService(): CocktailService {
        return CocktailService.create()
    }
}