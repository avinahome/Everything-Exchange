package com.everythingexchange.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.everythingexchange.app.ui.screens.auth.LoginScreen
import com.everythingexchange.app.ui.screens.main.MainScreen
import com.everythingexchange.app.viewmodel.AuthViewModel
import com.everythingexchange.app.utils.SessionManager
import androidx.compose.ui.platform.LocalContext
import com.everythingexchange.app.EverythingExchangeApplication

@Composable
fun EverythingExchangeApp(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val application = context.applicationContext as EverythingExchangeApplication
    val sessionManager = SessionManager(context)
    
    val authViewModel: AuthViewModel = viewModel { 
        AuthViewModel(
            userRepository = application.userRepository,
            sessionManager = sessionManager
        )
    }
    
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("main") { inclusive = true }
            }
        }
    }
    
    NavHost(
        navController = navController,
        startDestination = if (sessionManager.isLoggedIn()) "main" else "login"
    ) {
        composable("login") {
            LoginScreen(authViewModel = authViewModel)
        }
        composable("main") {
            MainScreen()
        }
    }
}