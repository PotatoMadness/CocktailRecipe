package com.potatomadness.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.model.Cocktail
import com.potatomadness.model.Step
import com.potatomadness.model.ingredientThumbNailImageUrl

@Composable
fun CocktailRecipeScreen(
    onRecipeStepClick: (String) -> Unit,
    onFabClick: (Int) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: DrinkDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    CocktailRecipeScreen(
        onRecipeStepClick = onRecipeStepClick,
        uiState = uiState,
        onFavoriteClick = { isFavorite, id -> viewModel.toggleFavorite(isFavorite, id) },
        onFabClick = onFabClick,
        onBackPressed = onBackPressed
    )
}

@Composable
fun CocktailRecipeScreen(
    onRecipeStepClick: (String) -> Unit,
    onFabClick: (Int) -> Unit,
    uiState: DetailUiState,
    onFavoriteClick: (Boolean, Int) -> Unit = { _, _ -> },
    onBackPressed: () -> Unit
) {
    when (uiState) {
        DetailUiState.Loading, DetailUiState.Failed -> Unit
        is DetailUiState.Success -> {
            Scaffold(topBar = {
                // topbar
                CocktailRecipeAppBar(
                    isFavorite = uiState.isFavorite,
                    cocktail = uiState.cocktail,
                    onFavoriteClick = onFavoriteClick
                ) {
                    onBackPressed()
                }
            }) { padding ->
                // contents
                Surface(modifier = Modifier.padding(padding)) {
                    CocktailDetailContent(
                        onRecipeStepClick = onRecipeStepClick,
                        onFabClick = onFabClick,
                        cocktail = uiState.cocktail
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailRecipeAppBar(
    isFavorite: Boolean,
    cocktail: Cocktail,
    onFavoriteClick: (Boolean, Int) -> Unit,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            val icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
            IconButton(onClick = { onFavoriteClick(isFavorite, cocktail.id) }) {
                Icon(imageVector = icon, contentDescription = "")
            }
        })
}

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
                top = 20.dp, start = 16.dp, end = 16.dp, bottom = 0.dp
            )
        ) {
            item {
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val minimumImageWidth = 184.dp
                val calculatedImageWidth = screenWidth * 0.3f
                val imageSize = maxOf(minimumImageWidth, calculatedImageWidth)
                GlideImage(
                    model = cocktail.thumbnailUrl,
                    contentDescription = stringResource(id = com.potatomadness.ui.R.string.description_picture_of_cocktail),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(imageSize),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.title_instructions),
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
                    text = stringResource(R.string.title_recipe),
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
            contentDescription = stringResource(id = com.potatomadness.ui.R.string.description_picture_of_ingredient),
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

@Preview(widthDp = 600, heightDp = 400)
@Composable
private fun testDetail() {
    CocktailRecipeScreen(
        onRecipeStepClick = {},
        onFabClick = {},
        uiState = DetailUiState.Success(isFavorite = true, cocktail = Cocktail(
            name = "asdf",
            thumbnailUrl = "www.thecocktaildb.com/images/ingredients/Applejack-small.png",
            id = 1,
            recipeSteps = listOf()
        )),
        onBackPressed = {}
    )
}