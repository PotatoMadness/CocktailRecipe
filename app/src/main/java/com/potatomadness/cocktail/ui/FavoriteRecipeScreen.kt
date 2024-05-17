package com.potatomadness.cocktail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.cocktail.data.Cocktail

@Composable
fun FavoriteRecipeScreen(
    viewModel: FavoriteRecipeViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    val favoriteList by viewModel.favoriteList.collectAsState(initial = listOf())
    LazyColumn(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text(
                modifier = Modifier.padding(12.dp),
                text = "즐겨찾는 칵테일",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        items(favoriteList) {
            FavoriteCocktailItem(it, onClick)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoriteCocktailItem(
    cocktail: Cocktail,
    onClick: (Int) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth().padding(horizontal = 8.dp),
        onClick = { onClick(cocktail.id) }) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            GlideImage(
                model = cocktail.thumbnailUrl,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().size(120.dp),
                contentScale = ContentScale.Fit)
            Text(text = cocktail.name,
                style = MaterialTheme.typography.titleMedium)
        }
    }
}