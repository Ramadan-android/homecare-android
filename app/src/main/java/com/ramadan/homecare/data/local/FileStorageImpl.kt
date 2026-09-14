package com.ramadan.homecare.data.local

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.webkit.MimeTypeMap
import com.ramadan.homecare.domain.storage.FileStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import androidx.core.net.toUri
import com.ramadan.homecare.domain.storage.SavedFile

class FileStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileStorage {
    override suspend fun saveFile(source: String): SavedFile {
        val uri = source.toUri()
        val mimeType = context.contentResolver.getType(uri)
        val extension = MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(mimeType)
            ?: throw IOException("Unsupported image type")
        val fileName = "homeCare_asset_${System.currentTimeMillis()}.$extension"
        val file = File(context.filesDir, fileName)

        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: throw IOException("Unable to open image")
        return SavedFile(
            fileReference = file.absolutePath,
            fileName = fileName,
            mimeType = mimeType ?: "",
            fileSize = file.length()
        )
    }

    override suspend fun openFile(
        context: Context,
        fileReference: String,
        mimeType: String

    ) {
        val uri = fileReference.toUri()
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // No app installed that can open this file
        }



    }

    override suspend fun deleteFile(fileReference: String) {
        val file = File(fileReference)
        if (file.exists()) {
            file.delete()
        }
    }

}