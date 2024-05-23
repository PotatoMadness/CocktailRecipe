package com.potatomadness.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Ingredient")
data class Ingredient(
    @PrimaryKey @ColumnInfo(name = "name")
    @field:SerializedName("strIngredient") val name: String,
    @field:SerializedName("strDescription") val description: String? = null,
    @field:SerializedName("strType") val type: String? = null,
    @field:SerializedName("strAlcohol") val strAlcohol: String? = null,
    @field:SerializedName("strABV") val strABV: String? = null,
) {
    val aBV: String
        get() = if (strAlcohol == "Yes") strABV ?: "None" else "None"
}

val String.ingredientThumbNailImageUrl: String
    get() = "https://www.thecocktaildb.com/images/ingredients/$this-Small.png"

val String.ingredientImageUrl: String
    get() = "https://www.thecocktaildb.com/images/ingredients/$this-Medium.png"

