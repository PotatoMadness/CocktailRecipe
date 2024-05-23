package com.potatomadness.favorite

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.data.model.Cocktail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteRecipeScreen(
    viewModel: FavoriteRecipeViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    val favoriteList by viewModel.favoriteList.collectAsState(initial = listOf())
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "즐겨찾는 칵테일",
                    style = MaterialTheme.typography.headlineMedium
                )
            })
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            if (favoriteList.isNullOrEmpty()) {
                // null screen
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .size(240.dp, 200.dp)
                            .align(Alignment.Center)
                            .border(2.dp, Color.LightGray, RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            text = "No Favorite Cocktail",
                            maxLines = 3,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.LightGray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favoriteList) {
                        FavoriteCocktailItem(it, onClick)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoriteCocktailItem(
    cocktail: Cocktail,
    onClick: (Int) -> Unit
) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
        onClick = { onClick(cocktail.id) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlideImage(
                model = cocktail.thumbnailUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}