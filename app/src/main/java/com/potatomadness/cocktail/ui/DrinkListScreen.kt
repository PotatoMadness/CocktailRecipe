package com.potatomadness.cocktail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.cocktail.data.Drinks

@Composable
fun DrinkListScreen(
    drinks: List<Drinks>,
    onDrinkClick: (Drinks) -> Unit = {}
) {
    filteredDrinks(drinks, onDrinkClick)
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun filteredDrinks(
    drinks: List<Drinks>,
    onDrinkClick: (Drinks) -> Unit = {}
) {
    LazyColumn(modifier = Modifier.padding(10.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(drinks) {
            Card (onClick = { onDrinkClick(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)){
                Row {
                    GlideImage(
                        model = it.thumbnailUrl,
                        contentDescription = "picture of cocktail",
                        modifier = Modifier
                            .width(84.dp)
                            .height(84.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(text = it.name, style = MaterialTheme.typography.displaySmall)
                }
            }
        }
    }
}