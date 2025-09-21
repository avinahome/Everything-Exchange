package com.everythingexchange.app.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OffersScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Offers",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Tabs for different offer types
        var selectedTab by remember { mutableStateOf(0) }
        val tabs = listOf("Received", "Sent")
        
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        when (selectedTab) {
            0 -> ReceivedOffersContent()
            1 -> SentOffersContent()
        }
    }
}

@Composable
private fun ReceivedOffersContent() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(3) { index ->
            MockOfferCard(
                title = "Offer for your Vintage Bicycle",
                amount = "$${120 + index * 10}",
                status = when (index) {
                    0 -> "Pending"
                    1 -> "Accepted"
                    else -> "Declined"
                },
                isReceived = true
            )
        }
    }
}

@Composable
private fun SentOffersContent() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(2) { index ->
            MockOfferCard(
                title = "Your offer for Coffee Table",
                amount = "$${65 + index * 5}",
                status = if (index == 0) "Pending" else "Declined",
                isReceived = false
            )
        }
    }
}

@Composable
private fun MockOfferCard(
    title: String,
    amount: String,
    status: String,
    isReceived: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = amount,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Status: $status",
                style = MaterialTheme.typography.bodySmall,
                color = when (status) {
                    "Accepted" -> MaterialTheme.colorScheme.primary
                    "Declined" -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            
            if (isReceived && status == "Pending") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { /* Mock decline */ }) {
                        Text("Decline")
                    }
                    TextButton(onClick = { /* Mock accept */ }) {
                        Text("Accept")
                    }
                }
            }
        }
    }
}