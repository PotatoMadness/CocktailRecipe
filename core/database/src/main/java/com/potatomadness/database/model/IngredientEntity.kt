package com.potatomadness.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.potatomadness.model.Cocktail
import com.potatomadness.model.Ingredient

@Entity(tableName = "Ingredient")
data class IngredientEntity(
    @PrimaryKey @ColumnInfo(name = "name")
    val name: String,
    val description: String? = null,
    val type: String? = null,
    val alcohol: String? = null,
    val abv: String? = null,
) {
    constructor(ingredient: Ingredient): this(
        ingredient.name,
        ingredient.description,
        ingredient.type,
        ingredient.strAlcohol,
        ingredient.strABV
    )
}

fun IngredientEntity.asExternalModel() = Ingredient(
    name = name,
    description = description,
    type = type,
    strAlcohol = alcohol,
    strABV = abv
)