package com.potatomadness.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson

class RecipeConverter {
    @TypeConverter
    fun recipeToJson(value: List<com.potatomadness.data.model.Step>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToRecipe(value: String): List<com.potatomadness.data.model.Step>? {
        return Gson().fromJson(value, Array<com.potatomadness.data.model.Step>::class.java)?.toList()
    }
}