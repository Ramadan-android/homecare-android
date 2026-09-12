package com.ramadan.homecare.domain.repository

import com.ramadan.homecare.domain.model.Attachment

interface AttachmentRepository {

    suspend fun insertAttachment(attachment: Attachment)

    suspend fun getAllAssetAttachments(assetId: Long): List<Attachment>

    suspend fun getAttachmentsByAssetIdAndMaintenanceId(assetId: Long, maintenanceId: Long): List<Attachment>

    suspend fun getAssetLevelAttachments(assetId: Long): List<Attachment>

    suspend fun deleteAttachmentById(attachmentId: Long)

    suspend fun deleteAttachmentsByAssetId(assetId: Long)
}