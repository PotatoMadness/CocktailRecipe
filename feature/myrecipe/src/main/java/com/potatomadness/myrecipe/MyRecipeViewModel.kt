package com.potatomadness.myrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.data.repository.MyRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyRecipeViewModel @Inject constructor(
    myRecipeRepository: MyRecipeRepository
): ViewModel(){
    val myCocktails = myRecipeRepository.getAllMyRecipe().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
}