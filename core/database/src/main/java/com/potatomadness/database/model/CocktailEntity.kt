package com.potatomadness.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.potatomadness.model.Cocktail
import com.potatomadness.model.Step


@Entity(tableName = "Cocktail")
data class CocktailEntity(
    var name: String,
    val thumbnailUrl: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int = 0,
    val category: String? = null,
    val alcoholic: String? = null,
    val glass: String? = null,
    var instructions: String? = null,
    val isCustom: Boolean = false,
    val isFavorite: Boolean = false,
    val recipeSteps: List<Step> = listOf()
) {
    constructor(cocktail: Cocktail): this(
        cocktail.name,
        cocktail.thumbnailUrl,
        cocktail.id,
        cocktail.category,
        cocktail.alcoholic,
        cocktail.glass,
        cocktail.instructions,
        cocktail.isCustom,
        cocktail.isFavorite,
        cocktail.recipeSteps
    )
}

fun CocktailEntity.asExternalModel() = Cocktail(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category,
    alcoholic = alcoholic,
    glass = glass,
    instructions = instructions,
    isFavorite = isFavorite,
    isCustom = isCustom,
    recipeSteps = recipeSteps
)