package com.fakhri.notes.screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        NotesScreen.Home,
        NotesScreen.Favorite // Tambahkan item "Favorite"
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val currentRoute = navController.currentDestination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        NotesScreen.Home -> Icon(
                            Icons.Filled.Home,
                            contentDescription = screen.title
                        )

                        NotesScreen.Favorite -> Icon(
                            Icons.Filled.Favorite,
                            contentDescription = screen.title
                        ) // Ikon untuk "Favorite"
                        else -> Icon(Icons.Filled.Home, contentDescription = screen.title)
                    }
                },
                label = { Text(screen.title) },
                selected = currentRoute == screen.name,
                onClick = {
                    navController.navigate(screen.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
