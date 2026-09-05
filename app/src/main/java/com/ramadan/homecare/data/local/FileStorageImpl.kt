package com.ramadan.homecare.data.local

import android.content.Context
import android.webkit.MimeTypeMap
import com.ramadan.homecare.domain.storage.FileStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import androidx.core.net.toUri

class FileStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileStorage {
    override suspend fun saveImage(source: String): String {
        val uri = source.toUri()
        val mimeType = context.contentResolver.getType(uri)
        val extension = MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(mimeType)
            ?: throw IOException("Unsupported image type")
        val fileName = "homeCare_asset_${System.currentTimeMillis()}.$extension"
        val file = File(context.filesDir, fileName)

        context.contentResolver.openInputStream(uri)?.use {input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: throw IOException("Unable to open image")
        return file.absolutePath
    }

    override suspend fun deleteFile(fileReference: String) {
        val file = File(fileReference)
        if (file.exists()) {
            file.delete()
        }
    }

}