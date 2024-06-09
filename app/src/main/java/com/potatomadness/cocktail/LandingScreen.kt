package com.potatomadness.cocktail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potatomadness.designsystem.theme.CocktailRecipeTheme
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@Composable
fun LandingScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(Unit) {
        delay(SplashWaitTime)
        currentOnTimeout()
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.primaryContainer)) {
        Column(modifier = Modifier
            .width(200.dp)
            .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Image(
                painterResource(id = R.drawable.cocktail_shaker),
                contentDescription = null,
                Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.app_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview
@Composable
fun previewLanding() {
    CocktailRecipeTheme {
        LandingScreen() {

        }
    }
}