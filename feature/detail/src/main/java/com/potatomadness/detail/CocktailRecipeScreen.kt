package com.potatomadness.detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.data.model.Cocktail
import com.potatomadness.data.model.Step
import com.potatomadness.data.model.ingredientThumbNailImageUrl

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun CocktailRecipeScreen(
    onRecipeStepClick: (String) -> Unit,
    onFabClick: (Int) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: DrinkDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState(initial = false)
    CocktailRecipeScreen(
        isFavorite = isFavorite,
        onRecipeStepClick = onRecipeStepClick,
        cocktail = uiState.cocktail,
        onFavoriteClick = { isFavorite -> viewModel.toggleFavorite(isFavorite) },
        onFabClick = onFabClick,
        onBackPressed = onBackPressed
    )
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun CocktailRecipeScreen(
    isFavorite: Boolean = false,
    onRecipeStepClick: (String) -> Unit,
    onFabClick: (Int) -> Unit,
    cocktail: Cocktail?,
    onFavoriteClick: (Boolean) -> Unit = {},
    onBackPressed: () -> Unit
) {
    if (cocktail == null) return
    Box(modifier = Modifier.fillMaxSize()) {
        // topbar
        CocktailRecipeAppBar(
            isFavorite = isFavorite,
            cocktail = cocktail,
            onFavoriteClick = onFavoriteClick
        ) {
            onBackPressed()
        }
        // contents
        CocktailDetailContent(
            onRecipeStepClick = onRecipeStepClick,
            onFabClick = onFabClick,
            cocktail = cocktail
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailRecipeAppBar(
    isFavorite: Boolean,
    cocktail: Cocktail,
    onFavoriteClick: (Boolean) -> Unit,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "${cocktail.name}",
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
        },
        actions = {
            val icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
            IconButton(onClick = { onFavoriteClick(isFavorite) }) {
                Icon(imageVector = icon, contentDescription = "")
            }
        })
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CocktailDetailContent(
    onRecipeStepClick: (String) -> Unit,
    onFabClick: (Int) -> Unit,
    cocktail: Cocktail
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(
                top = 80.dp, start = 16.dp, end = 16.dp, bottom = 0.dp
            )
        ) {
            item {
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val minimumImageWidth = 100.dp
                val calculatedImageWidth = screenWidth * 0.4f
                val imageSize = maxOf(minimumImageWidth, calculatedImageWidth)
                GlideImage(
                    model = cocktail.thumbnailUrl,
                    contentDescription = "picture of cocktail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(imageSize),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = cocktail.instructions ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Recipe",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            items(cocktail.recipeSteps) {
                RecipeStep(it, onRecipeStepClick)
                Spacer(Modifier.height(4.dp))
            }

            item {
                Spacer(Modifier.height(48.dp))
            }
        }
        FloatingActionButton(
            onClick = { onFabClick(cocktail.id) },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Filled.Create, contentDescription = "new recipe")
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeStep(
    step: Step,
    onIngredientClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .clickable { onIngredientClick(step.ingName) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlideImage(
            model = step.ingName.ingredientThumbNailImageUrl,
            contentDescription = "picture of ingredient",
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.FillHeight,
        )
        Text(
            modifier = Modifier.weight(1f),
            text = step.ingName,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.weight(1f),
            text = step.amount,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@OptIn(ExperimentalGlideComposeApi::class)
@Preview(widthDp = 600, heightDp = 400)
@Composable
private fun testDetail() {
    CocktailRecipeScreen(
        onRecipeStepClick = {},
        onFabClick = {},
        cocktail = Cocktail(
            name = "asdf",
            thumbnailUrl = "www.thecocktaildb.com/images/ingredients/Applejack-small.png",
            id = 1,
            recipeSteps = listOf()
        )
    ) {

    }
}