package com.potatomadness.data.repository

import com.potatomadness.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface PopularRecipeRepository {
    val popularRecipeFlow: Flow<List<Cocktail>>
}