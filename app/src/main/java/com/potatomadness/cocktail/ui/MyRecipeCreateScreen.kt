package com.potatomadness.cocktail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    ) {
        // 복사해온 리스트 있으면 세팅
        // 이름
        // 설명
        // TODO :: 이미지 업로드 -> db에만 저장 -> dao에 저장타입 분기
        // 재료추가 -> 추가화면 : 모은 재료 리스트

    }
}