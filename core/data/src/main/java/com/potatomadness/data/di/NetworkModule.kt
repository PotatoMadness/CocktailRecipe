package com.potatomadness.data.di

import com.potatomadness.data.api.CocktailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideCocktailService(): CocktailService {
        return CocktailService.create()
    }
}