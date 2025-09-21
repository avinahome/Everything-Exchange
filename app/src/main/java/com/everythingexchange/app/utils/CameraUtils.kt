package com.everythingexchange.app.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraUtils {
    companion object {
        fun createImageFile(context: Context): Uri {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val imageFileName = "JPEG_${timeStamp}_"
            val storageDir: File? = context.getExternalFilesDir("Pictures")
            val imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
            )
            
            return FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
        }
    }
}