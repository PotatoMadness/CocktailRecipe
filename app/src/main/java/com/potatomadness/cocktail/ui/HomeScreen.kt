package com.potatomadness.cocktail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.potatomadness.cocktail.CocktailViewModel
import com.potatomadness.cocktail.ScreenType
import com.potatomadness.data.model.Cocktail
import com.potatomadness.data.model.FilterType

@Composable
fun HomeScreen(
    isExpanded: Boolean,
    onDrinkClick: (Int) -> Unit,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val filterTypes: List<FilterType> =
        FilterType::class.sealedSubclasses.map { it.objectInstance ?: FilterType.Alcoholic }
    val filterSelected by viewModel.filterType.collectAsState()
    val filterList by viewModel.filterList.collectAsState()

    val screenType = uiState.getPanelType(isExpanded)
    HomeScreen(
        screenType = screenType,
        cocktails = uiState.cocktailList,
        filterList = filterList,
        filterTypes = filterTypes,
        filterSelected = filterSelected,
        onTypeClick = { type -> viewModel.changeFilterType(type) },
        onFilterClick = { filter -> viewModel.getDrinkListByFilter(filter) },
        onAlphaClick = { alpha -> viewModel.searchDrinkListByAlpha(alpha) },
        onDrinkClick = onDrinkClick,
        onCloseListScreen = { viewModel.closeListScreen() }
    )
}
@Composable
fun HomeScreen(
    screenType: ScreenType,
    cocktails: List<Cocktail>?,
    filterList: List<String>,
    filterTypes: List<FilterType>,
    filterSelected: FilterType,
    onTypeClick: (FilterType) -> Unit,
    onFilterClick: (String) -> Unit,
    onAlphaClick: (String) -> Unit,
    onDrinkClick: (Int) -> Unit,
    onCloseListScreen: () -> Unit
){
    // 화면크기에 따라 two panel
    when(screenType) {
        ScreenType.FilterWithList ->
            FilterWithListScreen(
                cocktails = cocktails,
                filterList = filterList,
                filterTypes = filterTypes,
                filterSelected = filterSelected,
                onTypeClick = onTypeClick,
                onFilterClick = onFilterClick,
                onAlphaClick = onAlphaClick,
                onDrinkClick = onDrinkClick,
                onCloseListScreen = onCloseListScreen
            )
        ScreenType.Filters ->
            FilterScreen(
                filterList = filterList,
                filterTypes = filterTypes,
                filterSelected = filterSelected,

                onTypeClick = onTypeClick,
                onFilterClick = onFilterClick,
                onAlphaClick = onAlphaClick
            )
        ScreenType.List ->
            ListScreen(
                cocktails = cocktails,
                onDrinkClick = onDrinkClick,
                onCloseListScreen = onCloseListScreen
            )
    }
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun FilterWithListScreen(
    cocktails: List<Cocktail>?,
    filterList: List<String>,
    filterTypes: List<FilterType>,
    filterSelected: FilterType,
    onTypeClick: (FilterType) -> Unit,
    onFilterClick: (String) -> Unit,
    onAlphaClick: (String) -> Unit,
    onDrinkClick: (Int) -> Unit,
    onCloseListScreen: () -> Unit
) {
    Row {
        FilterPaneContent(
            modifier = Modifier.width(334.dp),
            filterList = filterList,
            filterTypes = filterTypes,
            filterSelected = filterSelected,
            onTypeClick = onTypeClick,
            onFilterClick = onFilterClick,
            onAlphaClick = onAlphaClick
        )
        if (!cocktails.isNullOrEmpty()) {
            DrinkListPaneContent(
                modifier = Modifier.fillMaxWidth(),
                cocktails = cocktails,
                onDrinkClick = onDrinkClick,
                onBackPressed = onCloseListScreen
            )
        }
    }
}

@Composable
fun FilterScreen(
    filterList: List<String>,
    filterTypes: List<FilterType>,
    filterSelected: FilterType,
    onTypeClick: (FilterType) -> Unit,
    onFilterClick: (String) -> Unit,
    onAlphaClick: (String) -> Unit,
) {
    FilterPaneContent(
        filterList = filterList,
        filterTypes = filterTypes,
        filterSelected = filterSelected,
        onTypeClick = onTypeClick,
        onFilterClick = onFilterClick,
        onAlphaClick = onAlphaClick
    )
}

@Composable
fun ListScreen(
    cocktails: List<Cocktail>?,
    onDrinkClick: (Int) -> Unit,
    onCloseListScreen: () -> Unit
) {
    if (cocktails == null) return
    DrinkListPaneContent(
        cocktails = cocktails,
        onDrinkClick = onDrinkClick,
        onBackPressed = onCloseListScreen
    )
}

@Composable
fun FilterPaneContent(
    modifier: Modifier = Modifier,
    filterList: List<String>,
    filterTypes: List<FilterType>,
    filterSelected: FilterType,
    onTypeClick: (FilterType) -> Unit,
    onFilterClick: (String) -> Unit,
    onAlphaClick: (String) -> Unit
) {
    val scrollableState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollableState)){
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlphaPicker(
    onAlphaClick: (String) -> Unit = {}
) {
    val alphaList = ('A'..'Z').toMutableList()
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = "알파벳 순으로 찾기", style = MaterialTheme.typography.headlineMedium)
        FlowRow(horizontalArrangement = Arrangement.Absolute.spacedBy(4.dp),) {
            alphaList.forEach {
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

@Preview("Home list detail screen", device = Devices.PIXEL_C)
@Composable
fun testFilterWithList() {
    HomeScreen(
        screenType = ScreenType.FilterWithList,
        cocktails = arrayListOf(Cocktail("a", "aaa", 2, "a", "a", "a", "a")),
        filterList = arrayListOf("al"),
        filterTypes = arrayListOf(FilterType.Alcoholic),
        filterSelected = FilterType.Alcoholic,
        onTypeClick = {},
        onFilterClick = {},
        onAlphaClick = {},
        onDrinkClick = {}
    ) {

    }
}