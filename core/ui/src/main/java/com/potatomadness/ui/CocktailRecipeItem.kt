package com.potatomadness.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CocktailRecipeItem(
    thumbnailUrl: String,
    name: String,
    id: Int,
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
) {
    Card (onClick = { onRecipeClick(id) },
        modifier = modifier
            .fillMaxWidth()){
        Row (verticalAlignment = Alignment.CenterVertically){
            GlideImage(
                model = thumbnailUrl,
                contentDescription = stringResource(R.string.description_picture_of_cocktail),
                modifier = Modifier
                    .width(84.dp)
                    .height(84.dp),
                contentScale = ContentScale.Fit
            )
            Text(text = name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}