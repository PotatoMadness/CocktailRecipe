package com.potatomadness.cocktail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.potatomadness.cocktail.CocktailViewModel
import com.potatomadness.cocktail.data.Drinks
import com.potatomadness.cocktail.data.FilterType

@Composable
fun HomeScreen(
    viewModel: CocktailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    ) {
    val filterTypes: List<String> = FilterType::class.sealedSubclasses.map { it.simpleName ?: "" }
    val filterSelected by viewModel.filterType.collectAsState()

    Column {
        val filterList by viewModel.filterList.collectAsState()
        val filteredDrinks by viewModel.filteredDrinks.collectAsState()
        LazyRow(userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(4.dp)) {
            items(filterTypes) {
                val selectedType = filterSelected::class.simpleName
                val bgColor = if (it.equals(selectedType, true)) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.secondaryContainer
                val txtColor = if (it.equals(selectedType, true)) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.secondary
                Box(modifier = Modifier
                    .background(color = bgColor, shape = RoundedCornerShape(4.dp))
                    .padding(8.dp)
                    ) {
                    Text(text = it, color = txtColor)
                }
            }
        }
        filterPicker(filters = filterList, onFilterClick = {
            // load cocktail list
            viewModel.getDrinkList(it)
        })
        Spacer(modifier = Modifier.height(16.dp))
        filteredDrinks(drinks = filteredDrinks)
    }
}

@Composable
fun filterPicker(
    filters: List<String>,
    onFilterClick: (String) -> Unit = {}
) {
    LazyRow {
        items(filters) {
            Button(modifier = Modifier.padding(8.dp),
                onClick = { onFilterClick(it) }) {
                Text(text = it)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun filteredDrinks(
    drinks: List<Drinks>,
    onDrinkClick: (Drinks) -> Unit = {}
) {
    LazyColumn(modifier = Modifier.padding(10.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(drinks) {
            Card (onClick = { onDrinkClick(it) },
                modifier = Modifier.padding(vertical = 12.dp)){
                Row {
                    GlideImage(
                        model = it.thumbnailUrl,
                        contentDescription = "picture of cocktail",
                        modifier = Modifier
                            .width(84.dp)
                            .height(84.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(text = it.name, style = MaterialTheme.typography.displaySmall)
                }
            }
        }
    }
}