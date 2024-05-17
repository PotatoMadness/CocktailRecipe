package com.potatomadness.cocktail.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRecipeScreen(
    onClickCreate: () -> Unit,
    viewModel: MyRecipeViewModel = hiltViewModel()
) {
    val recipes by viewModel.myCocktails.collectAsState(initial = listOf())
    Column {
        TopAppBar(title = { Text(text = "내가 만든 칵테일 레시피") })

        if (recipes.isNullOrEmpty()) {
            // null screen
            Box(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .size(240.dp, 200.dp)
                    .align(Alignment.Center)
                    .border(2.dp, Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable { onClickCreate() }
                ) {
                    Text(text = "Touch to Add\nNew Recipe\n+",
                        maxLines = 3,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.LightGray)
                }
            }
        } else {
            // list
            LazyColumn {
                items(recipes) {
                    FilteredDrinkItem(cocktail = it)
                }
            }
        }
    }
}