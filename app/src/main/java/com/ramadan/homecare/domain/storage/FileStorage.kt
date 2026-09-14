package com.ramadan.homecare.domain.storage

import android.content.Context

interface FileStorage {
    suspend fun saveFile(source: String): SavedFile

    suspend fun openFile(
        context: Context,
        fileReference: String,
        mimeType: String

    )

    suspend fun deleteFile(fileReference: String)
}

data class SavedFile(
    val fileReference: String,
    val fileName: String,
    val mimeType: String,
    val fileSize: Long
)