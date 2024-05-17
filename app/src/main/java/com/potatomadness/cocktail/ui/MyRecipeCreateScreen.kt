package com.potatomadness.cocktail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyRecipeCreateScreen(
    onBackPressed: () -> Unit,
    viewModel: MyRecipeCreateViewModel = hiltViewModel()
) {
    val cocktail by viewModel.newCocktail.collectAsState()
    val isValid by viewModel.isValid.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    var showDialog: RecipeDialogType by remember { mutableStateOf(RecipeDialogType.None) }
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "새 칵테일 레시피 만들기",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
    ) { padding ->
        if (showDialog !is RecipeDialogType.None) {
            SelectIngredientDialog(
                ingredients = ingredients,
                modifyStep = if(showDialog is RecipeDialogType.ModifyRecipe) (showDialog as RecipeDialogType.ModifyRecipe).step else null,
                onAddStep = { viewModel.addStep(it) },
                onDismissRequest = { showDialog = RecipeDialogType.None })
        }

        // 복사해온 리스트 있으면 세팅
        // 이름
        // 설명
        // TODO :: 이미지 업로드 -> db에만 저장 -> dao에 저장타입 분기
        // 재료추가 -> 추가화면 : 모은 재료 리스트

        Surface(modifier = Modifier.padding(padding)) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 24.dp)) {
                item {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = cocktail.name,
                        onValueChange = { viewModel.updateCocktailName(it) },
                        label = { Text(text = "칵테일 이름") })
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = cocktail.instructions ?: "",
                        onValueChange = { viewModel.updateCocktailInstruction(it) },
                        label = { Text(text = "설명") })
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "레시피",
                        style = MaterialTheme.typography.labelSmall)
                }
                if (cocktail.recipeSteps.isNotEmpty()) {
                    items(cocktail.recipeSteps) {
                        RecipeStep(step = it) { step ->
                            // modify step dialog
                            showDialog = RecipeDialogType.ModifyRecipe(step)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .border(2.dp, Color.LightGray, RoundedCornerShape(16.dp))
                        .clickable {
                            showDialog = RecipeDialogType.NewStep
                        }
                    ) {
                        Icon(modifier = Modifier.align(Alignment.Center), imageVector = Icons.Filled.Add,contentDescription = "")
                    }
                    Button(
                        enabled = isValid,
                        onClick = {
                            viewModel.createNewRecipe()
                        }) {
                        Text(text = "save")
                    }
                }
            }
        }
    }
}