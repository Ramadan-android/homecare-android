package com.ramadan.homecare.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.ramadan.homecare.core.util.Constants

@Entity(tableName = Constants.ATTACHMENTS_TABLE)
data class AttachmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val assetId: Long,
    val maintenanceId: Long?,
    val fileReference: String,
    val fileName: String,
    val mimeType: String
)
