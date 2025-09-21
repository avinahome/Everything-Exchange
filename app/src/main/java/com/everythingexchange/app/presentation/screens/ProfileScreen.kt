package com.everythingexchange.app.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.everythingexchange.app.data.database.AppDatabase
import com.everythingexchange.app.data.repository.UserRepository
import com.everythingexchange.app.presentation.viewmodel.UserViewModel

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val userRepository = UserRepository(database.userDao())
    val viewModel: UserViewModel = viewModel { UserViewModel(userRepository) }
    
    val currentUser by viewModel.currentUser.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    LaunchedEffect(error) {
        error?.let {
            // Handle error (would show snackbar in real app)
            viewModel.clearError()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentUser == null && !isLoading) {
            CreateAccountForm(
                onCreateAccount = { username, email, location ->
                    viewModel.createAccount(username, email, location)
                }
            )
        } else if (currentUser != null) {
            UserProfile(user = currentUser!!)
        }
        
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun CreateAccountForm(
    onCreateAccount: (String, String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium
            )
            
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = { onCreateAccount(username, email, location) },
                modifier = Modifier.fillMaxWidth(),
                enabled = username.isNotBlank() && email.isNotBlank() && location.isNotBlank()
            ) {
                Text("Create Account")
            }
        }
    }
}

@Composable
private fun UserProfile(user: com.everythingexchange.app.data.database.User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "Username: ${user.username}",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Text(
                text = "Email: ${user.email}",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Text(
                text = "Location: ${user.location}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}