package com.potatomadness.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.data.model.Step
import com.potatomadness.data.model.ingredientThumbNailImageUrl

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeStep(
    step: Step,
    onIngredientClick: (Step) -> Unit
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
            .clickable { onIngredientClick(step) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlideImage(
            model = step.ingName.ingredientThumbNailImageUrl,
            contentDescription = stringResource(R.string.description_picture_of_ingredient),
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