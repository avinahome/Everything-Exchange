package com.everythingexchange.app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.everythingexchange.app.presentation.screens.*

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Inventory : Screen("inventory", "Inventory", Icons.Default.Home)
    object Listings : Screen("listings", "Listings", Icons.Default.List)
    object Discover : Screen("discover", "Discover", Icons.Default.Search)
    object Offers : Screen("offers", "Offers", Icons.Default.ShoppingCart)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EverythingExchangeApp() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Inventory,
        Screen.Listings,
        Screen.Discover,
        Screen.Offers,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Profile.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Inventory.route) {
                InventoryScreen()
            }
            composable(Screen.Listings.route) {
                ListingsScreen()
            }
            composable(Screen.Discover.route) {
                DiscoverScreen()
            }
            composable(Screen.Offers.route) {
                OffersScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}