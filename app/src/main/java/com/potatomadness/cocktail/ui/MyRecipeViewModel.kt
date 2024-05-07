package com.potatomadness.cocktail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.cocktail.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyRecipeViewModel @Inject constructor(
    cocktailRepository: CocktailRepository
): ViewModel(){
    val myCocktails = cocktailRepository.getMyRecipes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
}