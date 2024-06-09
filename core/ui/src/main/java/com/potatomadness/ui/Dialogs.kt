package com.potatomadness.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.data.model.Ingredient
import com.potatomadness.data.model.Step
import com.potatomadness.data.model.ingredientThumbNailImageUrl

@Composable
fun SelectIngredientDialog(
    ingredients: List<Ingredient>,
    modifyStep: Step? = null,
    onAddStep: (Step) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selected: Ingredient? by remember { mutableStateOf(null) }
    var amount: String by remember {
        if(modifyStep != null) mutableStateOf(modifyStep.aBV)
        else mutableStateOf("")
    }
    val savedIngredient = if(modifyStep != null) Ingredient(
        name = modifyStep.ingName,
        strABV = modifyStep.aBV
    ) else null
    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = Modifier.fillMaxWidth()) {
            if (selected != null || modifyStep != null) {
                val ingredient = selected ?: savedIngredient ?: return@Card
                Column(horizontalAlignment = Alignment.End) {
                    Row {
                        IngredientItem(
                            modifier = Modifier.weight(1f),
                            ingredient = ingredient
                        ) { selected = null }
                        TextField(modifier = Modifier
                            .weight(1f)
                            .width(120.dp)
                            .padding(8.dp),
                            value = amount, onValueChange = {
                                amount = it
                            }, label = { Text(text = stringResource(R.string.string_amount)) })
                    }
                    TextButton(
                        onClick = {
                            onAddStep(Step(ingredient.name, amount))
                            onDismissRequest()
                        },
                        enabled = amount.isNotEmpty()
                    ) {
                        Text(text = stringResource(R.string.add_recipe))
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    columns = StaggeredGridCells.Adaptive(minSize = 120.dp),
                    content = {
                        items(ingredients) {
                            IngredientItem(ingredient = it,
                                onSelectIngredient = { it -> selected = it })
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IngredientItem(
    modifier: Modifier = Modifier,
    ingredient: Ingredient,
    onSelectIngredient: (Ingredient) -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable { onSelectIngredient(ingredient) }
            .wrapContentSize()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
    ) {
        Column(Modifier.padding(2.dp)) {
            GlideImage(
                model = ingredient.name.ingredientThumbNailImageUrl,
                contentDescription = "picture of ingredient",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            Text(
                text = ingredient.name,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = ingredient.aBV,
                style = MaterialTheme.typography.titleSmall,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

sealed class RecipeDialogType {
    data class ModifyRecipe(val step: Step): RecipeDialogType()
    object NewStep: RecipeDialogType()
    object None: RecipeDialogType()
}