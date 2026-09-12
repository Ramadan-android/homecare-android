package com.ramadan.homecare.data.repository.mappers

import com.ramadan.homecare.data.local.entity.AttachmentEntity
import com.ramadan.homecare.domain.model.Attachment

fun AttachmentEntity.toAttachment(): Attachment =
    Attachment(
        id = this.id,
        assetId = this.assetId,
        maintenanceId = this.maintenanceId,
        fileReference = this.fileReference,
        fileName = this.fileName,
        mimeType = this.mimeType,
        fileSize = this.fileSize
    )

fun Attachment.toEntity(): AttachmentEntity =
    AttachmentEntity(
        id = this.id,
        assetId = this.assetId,
        maintenanceId = this.maintenanceId,
        fileReference = this.fileReference,
        fileName = this.fileName,
        mimeType = this.mimeType,
        fileSize = this.fileSize
    )