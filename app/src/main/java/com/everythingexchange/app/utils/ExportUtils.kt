package com.everythingexchange.app.utils

import android.content.Context
import com.everythingexchange.app.data.entities.InventoryItem
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class ExportUtils {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        
        fun exportInventoryToCSV(context: Context, items: List<InventoryItem>, fileName: String): File {
            val file = File(context.getExternalFilesDir(null), "$fileName.csv")
            val writer = CSVWriter(FileWriter(file))
            
            // Write header
            writer.writeNext(arrayOf(
                "ID", "Name", "Description", "Category", "Purchase Price", 
                "Current Value", "Condition", "Photo URI", "Purchase Date", "Created At"
            ))
            
            // Write data
            items.forEach { item ->
                writer.writeNext(arrayOf(
                    item.id.toString(),
                    item.name,
                    item.description,
                    item.category,
                    item.purchasePrice?.toString() ?: "",
                    item.currentValue?.toString() ?: "",
                    item.condition,
                    item.photoUri ?: "",
                    item.purchaseDate?.let { dateFormat.format(Date(it)) } ?: "",
                    dateFormat.format(Date(item.createdAt))
                ))
            }
            
            writer.close()
            return file
        }
    }
}