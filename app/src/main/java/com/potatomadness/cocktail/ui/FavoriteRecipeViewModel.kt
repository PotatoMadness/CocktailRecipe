package com.potatomadness.cocktail.ui

import androidx.lifecycle.ViewModel
import com.potatomadness.cocktail.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    cocktailRepository: CocktailRepository
): ViewModel() {
    val favoriteList = cocktailRepository.getFavoriteCocktails()
}