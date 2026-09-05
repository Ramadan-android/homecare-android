package com.ramadan.homecare.domain.storage

interface FileStorage {
    suspend fun saveImage(source: String): String

    suspend fun deleteFile(fileReference: String)
}