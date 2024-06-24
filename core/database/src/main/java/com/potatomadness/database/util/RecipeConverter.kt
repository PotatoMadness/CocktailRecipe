package com.potatomadness.database.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.potatomadness.model.Step

class RecipeConverter {
    @TypeConverter
    fun recipeToJson(value: List<Step>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToRecipe(value: String): List<Step>? {
        return Gson().fromJson(value, Array<Step>::class.java)?.toList()
    }
}