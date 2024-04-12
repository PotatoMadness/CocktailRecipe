package com.potatomadness.cocktail.ui

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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

    CocktailDetailScreen(cocktail = uiState.cocktail, onBackPressed)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CocktailDetailScreen(cocktail: Cocktail?,
                         onBackPressed: () -> Unit) {
    if (cocktail == null) return
    Surface {
        Column {
            CockTailRecipeAppBar(cocktail) {
                onBackPressed()
            }
            GlideImage(
                model = cocktail.thumbnailUrl,
                contentDescription = "picture of cocktail",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(10.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(cocktail.recipeSteps) {
                    Row(modifier = Modifier.height(48.dp)) {
                        GlideImage(
                            model = it.first.ingredientImageUrl,
                            contentDescription = "picture of ingredient",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.Fit
                        )
                        Text(modifier = Modifier.weight(1f), text = it.first, style = MaterialTheme.typography.titleLarge)
                        Text(modifier = Modifier.weight(1f), text = it.second, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CockTailRecipeAppBar(cocktail: Cocktail,
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
@Preview(widthDp = 600, heightDp = 400)
@Composable
private fun testDetail() {
    CocktailDetailScreen(cocktail = Cocktail(
        name = "asdf",
        thumbnailUrl = "www.thecocktaildb.com/images/ingredients/Applejack-small.png",
        _ing1 = "Vodca",
        _measure1 = "1oc",
        id = "1"
    )) {

    }
}