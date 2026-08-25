package com.ramadan.homecare.domain.model

data class Attachment(
    val id: Long,
    val assetId: Long,
    val maintenanceId: Long?,
    val fileReference: String,
    val fileName: String,
    val mimeType: String
)
