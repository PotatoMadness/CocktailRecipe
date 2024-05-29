package com.potatomadness.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.potatomadness.data.model.Cocktail
import com.potatomadness.ui.CocktailRecipeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkListPaneContent(
    modifier: Modifier = Modifier,
    cocktails: List<Cocktail>,
    onDrinkClick: (Int) -> Unit = {},
    onBackPressed: () -> Unit
) {
    Scaffold (topBar = {
        FilteredDrinkListAppBar(cocktails) {
            onBackPressed()
        }
    }){ padding ->
        Surface(modifier = Modifier.padding(padding)) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp, 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(cocktails) {
                    CocktailRecipeItem(
                        thumbnailUrl = it.thumbnailUrl,
                        name = it.name,
                        id = it.id,
                        modifier = modifier,
                        onRecipeClick = onDrinkClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredDrinkListAppBar(
    cocktails: List<Cocktail>,
    onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Column {
                Text(text = "${cocktails.first().name} .. ${cocktails.last().name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(text = "${cocktails.size} drinks",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        })
}

