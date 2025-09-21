package com.everythingexchange.app.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun DiscoverScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Discover Nearby",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Mock search bar
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Search items...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            readOnly = true
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Mock nearby items
            items(10) { index ->
                MockItemCard(index)
            }
        }
    }
}

@Composable
private fun MockItemCard(index: Int) {
    val mockItems = listOf(
        "Vintage Bicycle" to "$150",
        "Coffee Table" to "$75",
        "Garden Tools Set" to "$45",
        "Books Collection" to "$25",
        "Kitchen Appliances" to "$200",
        "Outdoor Furniture" to "$300",
        "Electronics Bundle" to "$120",
        "Art Supplies" to "$35",
        "Sports Equipment" to "$85",
        "Home Decor Items" to "$60"
    )
    
    val item = mockItems[index % mockItems.size]
    val distance = "${Random.nextInt(1, 10)} miles away"
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.first,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = item.second,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = distance,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /* Mock action */ }) {
                    Text("Make Offer")
                }
            }
        }
    }
}