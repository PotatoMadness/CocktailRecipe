package com.potatomadness.myrecipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomadness.data.repository.CocktailRepository
import com.potatomadness.data.repository.MyRecipeRepository
import com.potatomadness.model.Cocktail
import com.potatomadness.model.Ingredient
import com.potatomadness.model.Step
import com.potatomadness.myrecipe.navigation.RecipeArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRecipeCreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
    private val myRecipeRepository: MyRecipeRepository
): ViewModel() {
    private val recipeArgs: RecipeArgs = RecipeArgs(savedStateHandle)
    private val cocktailId: Int = recipeArgs.id
    private val newCocktail: MutableStateFlow<Cocktail> = MutableStateFlow(Cocktail())
    private val updateResult = MutableStateFlow(false)

    val uiState: StateFlow<CreateUiState> = createUiState(cocktailRepository)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), CreateUiState.Loading)

    private fun createUiState(
        cocktailRepository: CocktailRepository
    ): Flow<CreateUiState> {
        val ingredients = cocktailRepository.getIngredients()
        return combine(newCocktail, ingredients, updateResult, ::Triple).map {
            val (cocktail, ingredientList, result) = it
            when {
                result -> CreateUiState.DONE
                else -> {
                    val isExist = cocktailRepository.isExist(cocktail.name)
                    val isUpdatable = (cocktail.recipeSteps.isNotEmpty()
                        && !cocktail.instructions.isNullOrEmpty()
                        && cocktail.name.isNotEmpty()
                        && !isExist)
                    CreateUiState.Editing(cocktail, isUpdatable, ingredientList)
                }
            }
        }
    }
    init {
        if (cocktailId > 0) {
            viewModelScope.launch {
                val initialRecipe = cocktailRepository.getCocktailRecipe(cocktailId).first()
                newCocktail.emit(initialRecipe)
            }
        }
    }

    fun addStep(step: Step) {
        viewModelScope.launch {
            val steps: MutableList<Step> = newCocktail.value.recipeSteps.toMutableList().apply { add(step) }
            newCocktail.emit(newCocktail.value.copy(recipeSteps = steps.toList()))
        }
    }

    fun updateCocktailName(name: String) {
        viewModelScope.launch {
            newCocktail.emit(newCocktail.value.copy(name = name))
        }
    }

    fun updateCocktailInstruction(contents: String) {
        viewModelScope.launch {
            newCocktail.emit(newCocktail.value.copy(instructions = contents))
        }
    }

    fun createNewRecipe() {
        viewModelScope.launch {
            myRecipeRepository.addNewRecipe(newCocktail.value)
            updateResult.value = true
        }
    }
}

sealed interface CreateUiState {
    object Loading: CreateUiState
    data class Editing(val recipe: Cocktail, val isUpdatable: Boolean, val ingredients: List<Ingredient>): CreateUiState
    object DONE: CreateUiState
}