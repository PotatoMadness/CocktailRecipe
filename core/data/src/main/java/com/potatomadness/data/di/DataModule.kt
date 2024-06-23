package com.potatomadness.data.di

import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.data.repository.DefaultFavoriteRepository
import com.potatomadness.data.repository.DefaultMyRecipeRepository
import com.potatomadness.data.repository.FavoriteRepository
import com.potatomadness.data.repository.MyRecipeRepository
import com.potatomadness.data.repository.OnlineCocktailRepository
import com.potatomadness.data.repository.OnlinePopularRecipeRepository
import com.potatomadness.data.repository.PopularRecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsPopularRecipeRepository(
        popularRecipeRepository: OnlinePopularRecipeRepository
    ): PopularRecipeRepository

    @Binds
    internal abstract fun bindsMyRecipeRepository(
        myRecipeRepository: DefaultMyRecipeRepository
    ): MyRecipeRepository

    @Binds
    internal abstract fun bindsFavoriteRepository(
        favoriteRepository: DefaultFavoriteRepository
    ): FavoriteRepository

    @Binds
    internal abstract fun bindsCocktailRepository(
        cocktailRepository: OnlineCocktailRepository
    ): CocktailRepository
}