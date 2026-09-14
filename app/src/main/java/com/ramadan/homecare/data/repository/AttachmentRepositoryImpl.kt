package com.ramadan.homecare.data.repository

import com.ramadan.homecare.data.local.dao.AttachmentDao
import com.ramadan.homecare.data.repository.mappers.toAttachment
import com.ramadan.homecare.data.repository.mappers.toEntity
import com.ramadan.homecare.domain.model.Attachment
import com.ramadan.homecare.domain.repository.AttachmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AttachmentRepositoryImpl @Inject constructor(
    private val attachmentDao: AttachmentDao
): AttachmentRepository{
    override suspend fun insertAttachment(attachment: Attachment) {
        attachmentDao.insertAttachment(attachment.toEntity())
    }

    override fun getAllAssetAttachments(assetId: Long): Flow<List<Attachment>> {
        return attachmentDao.getAllAssetAttachments(assetId).map {attachmentEntities ->
            attachmentEntities.map { it.toAttachment() }
        }
    }

    override suspend fun getAttachmentsByAssetIdAndMaintenanceId(
        assetId: Long,
        maintenanceId: Long
    ): List<Attachment> {
        return attachmentDao.getAttachmentsByAssetIdAndMaintenanceId(
            assetId = assetId,
            maintenanceId = maintenanceId
        ).map { it.toAttachment() }
    }

    override suspend fun getAssetLevelAttachments(assetId: Long): List<Attachment> {
        return attachmentDao.getAssetLevelAttachments(assetId).map { it.toAttachment() }
    }

    override suspend fun deleteAttachmentById(attachmentId: Long) {
        attachmentDao.deleteAttachmentById(attachmentId)
    }

    override suspend fun deleteAttachmentsByAssetId(assetId: Long) {
        attachmentDao.deleteAttachmentsByAssetId(assetId)
    }

}