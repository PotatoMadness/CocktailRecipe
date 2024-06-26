package com.potatomadness.cocktail.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CocktailNavRail(
    currentRoute: String,
    navActions: CocktailAppNavigationActions
) {
    NavigationRail(modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        header = {
            Icon(Icons.Outlined.Send, contentDescription = "",
                modifier = Modifier.padding(vertical = 16.dp))
        }) {
        val destinations = TopLevelDestination::class.sealedSubclasses
            .sortedBy { it.objectInstance?.position }
            .map { it.objectInstance }
        destinations.forEach {
            if (it != null) {
                NavigationRailItem(
                    selected = currentRoute == it.route,
                    onClick = { navActions.navigateTo(it) },
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = it.icon, contentDescription = "")
                            Text(text = it.title, style = MaterialTheme.typography.labelMedium)
                        }
                    })
            }
        }
    }
}

@Composable
fun CocktailBottomNaviBar(
    currentRoute: String,
    navActions: CocktailAppNavigationActions
){
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        val destinations = TopLevelDestination::class.sealedSubclasses
            .sortedBy { it.objectInstance?.position }
            .map { it.objectInstance }
        destinations.forEach {
            if (it != null) {
                NavigationBarItem(
                    selected = currentRoute == it.route,
                    onClick = { navActions.navigateTo(it) },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = it.icon, contentDescription = "")
                            Text(text = it.title, style = MaterialTheme.typography.labelMedium)
                        }
                    })
            }
        }
    }
}
