package com.potatomadness.cocktail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.cocktail.CocktailRepository
import com.potatomadness.cocktail.Const.COCKTAIL_ID_SAVED_STATE_KEY
import com.potatomadness.cocktail.data.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRecipeCreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    cocktailRepository: CocktailRepository
): ViewModel() {
    val cocktailId: String = savedStateHandle.get<String>(COCKTAIL_ID_SAVED_STATE_KEY)!!
    private val _newCocktail: MutableStateFlow<Cocktail> = MutableStateFlow(Cocktail(name = "", thumbnailUrl = "", id = ""))
    val newCocktail: StateFlow<Cocktail> = _newCocktail

    init {
        viewModelScope.launch {
            val copyFrom = cocktailRepository.getDrinkRecipe(cocktailId)
            _newCocktail.emit(copyFrom)
        }
    }
}