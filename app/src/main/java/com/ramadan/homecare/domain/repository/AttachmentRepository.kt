package com.ramadan.homecare.domain.repository

import com.ramadan.homecare.domain.model.Attachment

interface AttachmentRepository {

    suspend fun insertAttachment(attachment: Attachment)

    suspend fun getAttachmentsByAssetIdAndMaintenanceId(assetId: Long, maintenanceId: Long): List<Attachment>

    suspend fun getAssetAttachments(assetId: Long): List<Attachment>

    suspend fun deleteAttachmentById(attachmentId: Long)
}