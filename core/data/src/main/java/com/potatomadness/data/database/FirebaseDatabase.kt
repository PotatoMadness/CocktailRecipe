package com.potatomadness.data.database

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.potatomadness.data.model.Cocktail
import com.potatomadness.data.model.CocktailResponse
import kotlinx.coroutines.flow.MutableStateFlow

class FirebaseDatabase {
    val popularRecipeFlow = MutableStateFlow<List<Cocktail>>(listOf())

    init {
        val drinks = Firebase.database.getReference("drinks")
        drinks.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Cocktail>()
                for (i in snapshot.children) {
                    i.getValue<CocktailResponse>()?.toCocktail()?.let {
                        list.add(it)
                    }
                }
                popularRecipeFlow.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Asdf","error : ${error.message}")
            }
        })
    }
}