package com.everythingexchange.app.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.Color
import com.everythingexchange.app.data.entities.InventoryItem
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PDFExportUtils {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        
        fun exportInventoryToPDF(context: Context, items: List<InventoryItem>, fileName: String): File {
            val file = File(context.getExternalFilesDir(null), "$fileName.pdf")
            val document = PdfDocument()
            
            val pageInfo = PdfDocument.PageInfo.Builder(612, 792, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas
            
            val titlePaint = Paint().apply {
                color = Color.BLACK
                textSize = 24f
                isAntiAlias = true
            }
            
            val headerPaint = Paint().apply {
                color = Color.BLACK
                textSize = 14f
                isAntiAlias = true
                isFakeBoldText = true
            }
            
            val bodyPaint = Paint().apply {
                color = Color.BLACK
                textSize = 12f
                isAntiAlias = true
            }
            
            // Title
            canvas.drawText("Inventory Report", 50f, 50f, titlePaint)
            canvas.drawText("Generated on: ${dateFormat.format(Date())}", 50f, 80f, headerPaint)
            
            var yPosition = 120f
            val lineHeight = 16f
            
            // Add inventory items
            items.forEach { item ->
                if (yPosition > 750f) {
                    // Start new page if needed
                    document.finishPage(page)
                    val newPageInfo = PdfDocument.PageInfo.Builder(612, 792, document.pages.size + 1).create()
                    val newPage = document.startPage(newPageInfo)
                    val newCanvas = newPage.canvas
                    newCanvas.drawText("Inventory Report (Continued)", 50f, 50f, titlePaint)
                    yPosition = 80f
                }
                
                canvas.drawText("${item.name}", 50f, yPosition, headerPaint)
                yPosition += lineHeight
                canvas.drawText("Description: ${item.description}", 50f, yPosition, bodyPaint)
                yPosition += lineHeight
                canvas.drawText("Category: ${item.category}", 50f, yPosition, bodyPaint)
                yPosition += lineHeight
                canvas.drawText("Condition: ${item.condition}", 50f, yPosition, bodyPaint)
                yPosition += lineHeight
                item.currentValue?.let { value ->
                    canvas.drawText("Value: $${String.format("%.2f", value)}", 50f, yPosition, bodyPaint)
                    yPosition += lineHeight
                }
                yPosition += lineHeight // Extra space between items
            }
            
            document.finishPage(page)
            
            try {
                document.writeTo(FileOutputStream(file))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            document.close()
            return file
        }
    }
}