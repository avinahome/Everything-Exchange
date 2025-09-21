package com.everythingexchange.app.utils

import android.content.Context
import com.everythingexchange.app.data.database.InventoryItem
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class ExportUtils(private val context: Context) {
    
    private val exportsDir: File by lazy {
        File(context.getExternalFilesDir(null), "exports").apply {
            if (!exists()) mkdirs()
        }
    }
    
    fun exportInventoryToCsv(items: List<InventoryItem>): File? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val csvFile = File(exportsDir, "inventory_export_$timeStamp.csv")
            
            val writer = CSVWriter(FileWriter(csvFile))
            
            // Write header
            writer.writeNext(arrayOf(
                "ID", "Name", "Description", "Category", "Price", "Created Date"
            ))
            
            // Write data
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            items.forEach { item ->
                writer.writeNext(arrayOf(
                    item.id.toString(),
                    item.name,
                    item.description,
                    item.category,
                    item.price?.toString() ?: "",
                    dateFormat.format(Date(item.createdAt))
                ))
            }
            
            writer.close()
            csvFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun exportInventoryToPdf(items: List<InventoryItem>): File? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val pdfFile = File(exportsDir, "inventory_export_$timeStamp.pdf")
            
            // Note: Basic PDF generation - would need iText implementation
            // For now, creating a simple text file as placeholder
            val content = buildString {
                appendLine("INVENTORY EXPORT")
                appendLine("Generated: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}")
                appendLine("=" * 50)
                appendLine()
                
                items.forEach { item ->
                    appendLine("Item: ${item.name}")
                    appendLine("Description: ${item.description}")
                    appendLine("Category: ${item.category}")
                    if (item.price != null) {
                        appendLine("Price: $${item.price}")
                    }
                    appendLine("Created: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(item.createdAt))}")
                    appendLine("-" * 30)
                    appendLine()
                }
            }
            
            pdfFile.writeText(content)
            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}