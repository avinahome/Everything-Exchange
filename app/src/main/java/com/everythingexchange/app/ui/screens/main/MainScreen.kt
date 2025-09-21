package com.everythingexchange.app.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.everythingexchange.app.R
import com.everythingexchange.app.ui.screens.inventory.InventoryScreen
import com.everythingexchange.app.ui.screens.listing.ListingScreen
import com.everythingexchange.app.ui.screens.discover.DiscoverScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val tabs = listOf(
        Triple(stringResource(R.string.inventory), Icons.Default.Home, "inventory"),
        Triple(stringResource(R.string.listings), Icons.Default.List, "listings"),
        Triple(stringResource(R.string.discover), Icons.Default.Search, "discover"),
        Triple(stringResource(R.string.profile), Icons.Default.Person, "profile")
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, (title, icon, route) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            navController.navigate(route) {
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
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "inventory",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("inventory") {
                InventoryScreen()
            }
            composable("listings") {
                ListingScreen()
            }
            composable("discover") {
                DiscoverScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )
        // TODO: Implement profile functionality
        Text("Profile functionality coming soon...")
    }
}