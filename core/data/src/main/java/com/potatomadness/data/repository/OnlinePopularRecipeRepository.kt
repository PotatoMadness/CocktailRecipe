package com.potatomadness.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.potatomadness.model.Cocktail
import com.potatomadness.data.model.CocktailResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

internal class OnlinePopularRecipeRepository @Inject constructor()
    : PopularRecipeRepository {
    private val _popularRecipes = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Cocktail>()
                for (i in snapshot.children) {
                    i.getValue<CocktailResponse>()?.toCocktail()?.let {
                        list.add(it)
                    }
                }
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        val drinks = Firebase.database.getReference("drinks")
        drinks.addValueEventListener(callback)
        awaitClose {
            Log.d("_popularRecipes", "Stopping firebase realtime db updates")
        }
    }
    override val popularRecipeFlow: Flow<List<Cocktail>> = _popularRecipes
}