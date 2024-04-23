package com.potatomadness.cocktail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.cocktail.data.Cocktail

@Composable
fun DrinkListPaneContent(
    modifier: Modifier = Modifier,
    cocktails: List<Cocktail>,
    onDrinkClick: (Cocktail) -> Unit = {},
    onBackPressed: () -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxWidth()
        .padding(10.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            FilteredDrinkListAppBar(cocktails) {
                onBackPressed()
            }
        }
        items(cocktails) {
            FilteredDrinkItem(it, onDrinkClick)
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

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilteredDrinkItem(
    cocktail: Cocktail,
    onDrinkClick: (Cocktail) -> Unit = {}
) {
    Card (onClick = { onDrinkClick(cocktail) },
        modifier = Modifier
            .fillMaxWidth()){
        Row (verticalAlignment = Alignment.CenterVertically){
            GlideImage(
                model = cocktail.thumbnailUrl,
                contentDescription = "picture of cocktail",
                modifier = Modifier
                    .width(84.dp)
                    .height(84.dp),
                contentScale = ContentScale.Fit
            )
            Text(text = cocktail.name, style = MaterialTheme.typography.displaySmall)
        }
    }
}