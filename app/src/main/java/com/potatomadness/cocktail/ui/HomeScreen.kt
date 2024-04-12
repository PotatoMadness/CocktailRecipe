package com.potatomadness.cocktail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.potatomadness.cocktail.CocktailViewModel
import com.potatomadness.cocktail.data.Drink
import com.potatomadness.cocktail.data.FilterType

@Composable
fun HomeScreen(
    onDrinkClick: (Drink) -> Unit,
    viewModel: CocktailViewModel = hiltViewModel()
    ) {
    val uiState by viewModel.uiState.collectAsState()
    val filterTypes: List<FilterType> = FilterType::class.sealedSubclasses.map { it.objectInstance ?: FilterType.Alcoholic }
    val filterSelected by viewModel.filterType.collectAsState()
    val filterList by viewModel.filterList.collectAsState()

    // TODO :: 화면크기에 따라 two panel 
    if (uiState.drinkList.isNullOrEmpty()) {
        FilterPaneContent(
            filterList = filterList,
            filterTypes = filterTypes,
            filterSelected = filterSelected,
            onTypeClick = { type -> viewModel.changeFilterType(type)},
            onFilterClick = { filter -> viewModel.getDrinkListByFilter(filter)},
            onAlphaClick = { alpha -> viewModel.searchDrinkListByAlpha(alpha)}
        )
    } else {
        uiState.drinkList?.let {
            DrinkListPaneContent(
                drinks = it,
                onDrinkClick = onDrinkClick) {
                viewModel.closeListScreen()
            }
        }
    }
}

@Composable
fun FilterPaneContent(
    filterList: List<String>,
    filterTypes: List<FilterType>,
    filterSelected: FilterType,
    onTypeClick: (FilterType) -> Unit,
    onFilterClick: (String) -> Unit,
    onAlphaClick: (String) -> Unit
) {
    val scrollableState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollableState)){
        FilterPicker(
            filters = filterList,
            filterTypes = filterTypes,
            filterSelected = filterSelected,
            onTypeClick =  onTypeClick,
            onFilterClick = onFilterClick
        )
        Spacer(modifier = Modifier.height(40.dp))
        AlphaPicker(
            onAlphaClick = onAlphaClick // TODO : query Tag -> enum
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterPicker(
    filters: List<String>,
    filterTypes: List<FilterType>,
    filterSelected: FilterType,
    onTypeClick: (FilterType) -> Unit = {},
    onFilterClick: (String) -> Unit = {}
) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = "필터로 찾기", style = MaterialTheme.typography.headlineMedium)
        LazyRow(userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filterTypes) {
                val isSelected = it::class == filterSelected::class
                val bgColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.secondaryContainer
                val txtColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.secondary
                Button(
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = bgColor),
                    onClick = { onTypeClick(it) }
                ) {
                    Text(text = it.tagName, color = txtColor)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(horizontalArrangement = Arrangement.Absolute.spacedBy(6.dp)) {
            filters.forEach {
                Button(
                    onClick = { onFilterClick(it) }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun AlphaPicker(
    onAlphaClick: (String) -> Unit = {}
) {
    val alphaList = ('A'..'Z').toMutableList()
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = "알파벳 순으로 찾기", style = MaterialTheme.typography.headlineMedium)
        LazyRow (userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(4.dp)) {
            items(alphaList) {
                Button(
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    onClick = { onAlphaClick(it.toString()) }
                ) {
                    Text(text = it.toString(), color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

