package com.potatomadness.cocktail.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Cocktail")
data class Cocktail(
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
    fun isSimplySaved(): Boolean = category.isNullOrEmpty() && alcoholic.isNullOrEmpty() && glass.isNullOrEmpty()
}

data class Step(
    val ingName: String,
    val amount: String,
    val aBV: String = ""
)