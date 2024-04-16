package com.potatomadness.cocktail.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.cocktail.data.Cocktail
import com.potatomadness.cocktail.data.ingredientImageUrl

@Composable
fun CocktailDetailScreen(
    onIngredientClick: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: DrinkDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    CocktailDetailScreen(onIngredientClick = onIngredientClick,
        cocktail = uiState.cocktail, onBackPressed)
}

@Composable
fun CocktailDetailScreen(
    onIngredientClick: (String) -> Unit,
    cocktail: Cocktail?,
    onBackPressed: () -> Unit) {
    if (cocktail == null) return

    // scrollview 처럼 사용될때 내부의 recycleview로 인해 nestedscroll
    //
    Box(modifier = Modifier.fillMaxSize()){
        // topbar
        CocktailRecipeAppBar(cocktail) {
            onBackPressed()
        }
        // contents
        CocktailDetailContent(
            onIngredientClick = onIngredientClick,
            cocktail = cocktail)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailRecipeAppBar(cocktail: Cocktail,
                         onBackPressed: () -> Unit){
    TopAppBar(
        title = {
            Text(text = "${cocktail.name}",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CocktailDetailContent(
    onIngredientClick: (String) -> Unit,
    cocktail: Cocktail
) {
    LazyColumn(modifier = Modifier.padding(
        top = 80.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)) {
        item {
            GlideImage(
                model = cocktail.thumbnailUrl,
                contentDescription = "picture of cocktail",
                modifier = Modifier.fillMaxWidth(),
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
            RecipeStep(it, onIngredientClick)
            Spacer(Modifier.height(4.dp))
        }

        item {
            Spacer(Modifier.height(48.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeStep(
    step: Pair<String, String>,
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
            .clickable { onIngredientClick(step.first) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlideImage(
            model = step.first.ingredientImageUrl,
            contentDescription = "picture of ingredient",
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.FillHeight,
        )
        Text(
            modifier = Modifier.weight(1f),
            text = step.first,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.weight(1f),
            text = step.second,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(widthDp = 600, heightDp = 400)
@Composable
private fun testDetail() {
    CocktailDetailScreen(
        onIngredientClick = {},
        cocktail = Cocktail(
        name = "asdf",
        thumbnailUrl = "www.thecocktaildb.com/images/ingredients/Applejack-small.png",
        _ing1 = "Vodca",
        _measure1 = "1oc",
        id = "1"
    )) {

    }
}