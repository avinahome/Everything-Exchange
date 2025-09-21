package com.everythingexchange.app.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.everythingexchange.app.data.database.AppDatabase
import com.everythingexchange.app.data.database.InventoryItem
import com.everythingexchange.app.data.repository.InventoryRepository
import com.everythingexchange.app.data.repository.UserRepository
import com.everythingexchange.app.presentation.viewmodel.InventoryViewModel
import com.everythingexchange.app.presentation.viewmodel.UserViewModel
import com.everythingexchange.app.utils.ExportUtils
import com.everythingexchange.app.utils.PhotoUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val inventoryRepository = InventoryRepository(database.inventoryItemDao())
    val userRepository = UserRepository(database.userDao())
    val photoUtils = PhotoUtils(context)
    val exportUtils = ExportUtils(context)
    
    val userViewModel: UserViewModel = viewModel { UserViewModel(userRepository) }
    val inventoryViewModel: InventoryViewModel = viewModel { 
        InventoryViewModel(inventoryRepository, photoUtils, exportUtils)
    }
    
    val currentUser by userViewModel.currentUser.collectAsState()
    val items by inventoryViewModel.items.collectAsState()
    val isLoading by inventoryViewModel.isLoading.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    
    // Load items when user is available
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            inventoryViewModel.loadItemsForUser(user.id)
        }
    }
    
    if (currentUser == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Please create an account first")
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Inventory") },
                actions = {
                    TextButton(onClick = { inventoryViewModel.exportToCsv() }) {
                        Text("Export CSV")
                    }
                    TextButton(onClick = { inventoryViewModel.exportToPdf() }) {
                        Text("Export PDF")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                InventoryItemCard(
                    item = item,
                    onDelete = { inventoryViewModel.deleteItem(item) }
                )
            }
        }
        
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
    
    if (showAddDialog) {
        AddItemDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { name, description, category, price ->
                inventoryViewModel.addItem(
                    userId = currentUser!!.id,
                    name = name,
                    description = description,
                    category = category,
                    price = price
                )
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun InventoryItemCard(
    item: InventoryItem,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Category: ${item.category}",
                style = MaterialTheme.typography.bodySmall
            )
            item.price?.let { price ->
                Text(
                    text = "Price: $$price",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}

@Composable
private fun AddItemDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, Double?) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Item") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Price (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val price = priceText.toDoubleOrNull()
                    onAdd(name, description, category, price)
                },
                enabled = name.isNotBlank() && description.isNotBlank() && category.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}