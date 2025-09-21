package com.everythingexchange.app.utils

import android.content.Context
import com.everythingexchange.app.data.entities.InventoryItem
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.UnitValue
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PDFExportUtils {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        
        fun exportInventoryToPDF(context: Context, items: List<InventoryItem>, fileName: String): File {
            val file = File(context.getExternalFilesDir(null), "$fileName.pdf")
            val writer = PdfWriter(file)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)
            
            // Title
            document.add(Paragraph("Inventory Report").setFontSize(20f))
            document.add(Paragraph("Generated on: ${dateFormat.format(Date())}").setFontSize(12f))
            document.add(Paragraph(" "))
            
            // Create table
            val table = Table(UnitValue.createPercentArray(floatArrayOf(1f, 2f, 3f, 1f, 1f, 1f)))
            table.setWidth(UnitValue.createPercentValue(100f))
            
            // Add headers
            table.addHeaderCell("ID")
            table.addHeaderCell("Name")
            table.addHeaderCell("Description")
            table.addHeaderCell("Category")
            table.addHeaderCell("Condition")
            table.addHeaderCell("Value")
            
            // Add data
            items.forEach { item ->
                table.addCell(item.id.toString())
                table.addCell(item.name)
                table.addCell(item.description)
                table.addCell(item.category)
                table.addCell(item.condition)
                table.addCell(item.currentValue?.toString() ?: "N/A")
            }
            
            document.add(table)
            document.close()
            
            return file
        }
    }
}