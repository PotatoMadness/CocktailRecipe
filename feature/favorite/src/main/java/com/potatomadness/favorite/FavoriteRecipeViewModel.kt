package com.potatomadness.favorite

import androidx.lifecycle.ViewModel
import com.potatomadness.data.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    cocktailRepository: CocktailRepository
): ViewModel() {
    val favoriteList = cocktailRepository.getFavoriteCocktails()
}