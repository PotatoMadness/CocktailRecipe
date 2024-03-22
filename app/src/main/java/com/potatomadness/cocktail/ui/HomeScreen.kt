package com.potatomadness.cocktail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.potatomadness.cocktail.CocktailViewModel
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
        })
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