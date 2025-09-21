package com.everythingexchange.app.ui.screens.inventory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.everythingexchange.app.R
import com.everythingexchange.app.EverythingExchangeApplication
import com.everythingexchange.app.data.entities.InventoryItem
import com.everythingexchange.app.utils.SessionManager
import com.everythingexchange.app.viewmodel.InventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen() {
    val context = LocalContext.current
    val application = context.applicationContext as EverythingExchangeApplication
    val sessionManager = SessionManager(context)
    
    val inventoryViewModel: InventoryViewModel = viewModel {
        InventoryViewModel(
            inventoryRepository = application.inventoryRepository,
            sessionManager = sessionManager
        )
    }
    
    val inventoryItems by inventoryViewModel.inventoryItems.collectAsState()
    val uiState by inventoryViewModel.uiState.collectAsState()
    
    var showAddDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            // Show success message (could use SnackBar)
            inventoryViewModel.clearMessages()
        }
    }
    
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            // Show error message (could use SnackBar)
            inventoryViewModel.clearMessages()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.inventory)) },
                actions = {
                    IconButton(onClick = { showExportDialog = true }) {
                        Icon(Icons.Default.FileDownload, contentDescription = "Export")
                    }
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_item))
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(inventoryItems) { item ->
                InventoryItemCard(
                    item = item,
                    onEdit = { /* TODO: Implement edit */ },
                    onDelete = { inventoryViewModel.deleteItem(item) }
                )
            }
            
            if (inventoryItems.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No items in inventory. Tap + to add your first item!",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
    
    if (showExportDialog) {
        ExportDialog(
            items = inventoryItems,
            onDismiss = { showExportDialog = false }
        )
    }
    
    if (showAddDialog) {
        AddItemDialogEnhanced(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, description, category, purchasePrice, currentValue, condition, photoUri ->
                inventoryViewModel.addItem(
                    name = name,
                    description = description,
                    category = category,
                    purchasePrice = purchasePrice,
                    currentValue = currentValue,
                    condition = condition,
                    photoUri = photoUri
                )
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryItemCard(
    item: InventoryItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Category: ${item.category}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "Condition: ${item.condition}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    item.currentValue?.let { value ->
                        Text(
                            text = "Value: $${String.format("%.2f", value)}",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.edit)) },
                            onClick = {
                                showMenu = false
                                onEdit()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.delete)) },
                            onClick = {
                                showMenu = false
                                onDelete()
                            }
                        )
                    }
                }
            }
            
            item.photoUri?.let { uri ->
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Item photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}