package com.potatomadness.cocktail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.data.model.Ingredient
import com.potatomadness.data.model.ingredientImageUrl


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IngredientInfoScreen(
    onBackPressed: () -> Unit = {},
    viewModel: IngredientInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    IngredientInfoScreen(onBackPressed, uiState.ingredient)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientInfoScreen(
    onBackPressed: () -> Unit = {},
    ingredient: Ingredient?
) {
    if (ingredient == null) return
    Scaffold (
       topBar = {
           TopAppBar(
               title = {
                   Text(text = "${ingredient.name}",
                       style = MaterialTheme.typography.displaySmall,
                       color = MaterialTheme.colorScheme.onSurfaceVariant,
                       maxLines = 1,
                       overflow = TextOverflow.Ellipsis
                   )
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
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()
            .padding(padding)) {
            IngredientInfoContent(ingredient)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IngredientInfoContent(
    ingredient: Ingredient
) {
    val scrollableState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollableState)
        .padding(16.dp)) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val minimumImageWidth = 100.dp
        val calculatedImageWidth = screenWidth * 0.4f
        val imageSize = maxOf(minimumImageWidth, calculatedImageWidth)

        GlideImage(
            model = ingredient.name.ingredientImageUrl,
            contentDescription = "picture of cocktail",
            modifier = Modifier.fillMaxWidth().size(imageSize),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(10.dp))

        Text(
            text = "description",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = ingredient.description?:ingredient.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(20.dp))
        if (ingredient.type != null) {
            Row {
                Text(
                    text = "Type",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = ingredient.type ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(Modifier.height(20.dp))
        }
        Row {
            Text(
                text = "Alcohol",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = ingredient.aBV,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(Modifier.height(36.dp))
    }
}