package com.potatomadness.myrecipe

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.potatomadness.ui.CocktailRecipeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRecipeScreen(
    onClickCreate: () -> Unit,
    onRecipeClick: (Int) -> Unit,
    viewModel: MyRecipeViewModel = hiltViewModel()
) {
    val recipes by viewModel.myCocktails.collectAsState(initial = listOf())
    Scaffold (topBar = {
            TopAppBar(title = { Text(text = "내가 만든 칵테일 레시피") })
        }) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            if (recipes.isNullOrEmpty()) {
                // null screen
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier
                        .size(240.dp, 200.dp)
                        .align(Alignment.Center)
                        .border(2.dp, Color.LightGray, RoundedCornerShape(16.dp))
                        .clickable { onClickCreate() }
                    ) {
                        Text(
                            text = "Touch to Add\nNew Recipe\n+",
                            maxLines = 3,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.LightGray
                        )
                    }
                }
            } else {
                // list
                LazyColumn(modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(recipes) {
                        CocktailRecipeItem(thumbnailUrl = it.thumbnailUrl, name = it.name, id = it.id, modifier = Modifier) {
                            onRecipeClick(it)
                        }
                    }
                }
            }
        }
    }
}