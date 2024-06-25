package com.potatomadness.myrecipe

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.potatomadness.model.Step
import com.potatomadness.ui.RecipeDialogType
import com.potatomadness.ui.RecipeStep
import com.potatomadness.ui.SelectIngredientDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyRecipeCreateScreen(
    onBackPressed: () -> Unit,
    viewModel: MyRecipeCreateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    when(uiState) {
        CreateUiState.Loading -> Unit
        CreateUiState.DONE -> { onBackPressed() }
        is CreateUiState.Editing -> {
            MyRecipeCreateScreen(
                onBackPressed = onBackPressed,
                uiState = uiState as CreateUiState.Editing,
                onAddStep = { viewModel.addStep(it)},
                onUpdateName = { viewModel.updateCocktailName(it) },
                onUpdateInstruction = { viewModel.updateCocktailInstruction(it) },
                onClickSave = { viewModel.createNewRecipe() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRecipeCreateScreen(
    onBackPressed: () -> Unit,
    uiState: CreateUiState.Editing,
    onAddStep: (Step) -> Unit,
    onUpdateName: (String) -> Unit,
    onUpdateInstruction: (String) -> Unit,
    onClickSave: () -> Unit) {
    val cocktail = uiState.recipe
    val isValid = uiState.isUpdatable
    val ingredients = uiState.ingredients
    var showDialog: RecipeDialogType by remember { mutableStateOf(RecipeDialogType.None) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_create_recipe),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                onAddStep = onAddStep,
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
                        onValueChange = { onUpdateName(it) },
                        label = { Text(text = stringResource(R.string.title_cocktail_name)) })
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = cocktail.instructions ?: "",
                        onValueChange = { onUpdateInstruction(it) },
                        label = { Text(text = stringResource(R.string.title_cocktail_instruction)) })
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = stringResource(R.string.title_recipe),
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
                        onClick = { onClickSave() }) {
                        Text(text = stringResource(R.string.button_save))
                    }
                }
            }
        }
    }
}