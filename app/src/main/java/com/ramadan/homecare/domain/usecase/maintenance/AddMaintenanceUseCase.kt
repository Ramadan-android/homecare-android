package com.ramadan.homecare.domain.usecase.maintenance

import com.ramadan.homecare.domain.model.Attachment
import com.ramadan.homecare.domain.model.MaintenanceRecord
import com.ramadan.homecare.domain.repository.AttachmentRepository
import com.ramadan.homecare.domain.repository.MaintenanceRecordRepository
import com.ramadan.homecare.domain.storage.FileStorage
import javax.inject.Inject

class AddMaintenanceUseCase @Inject constructor(
    private val fileStorage: FileStorage,
    private val attachmentRepository: AttachmentRepository,
    private val maintenanceRecordRepository: MaintenanceRecordRepository
) {
    suspend operator fun invoke(maintenanceRecord: MaintenanceRecord, attachments: List<String>) {
        val maintenanceId = maintenanceRecordRepository.insertMaintenance(maintenanceRecord)
        val attachments = attachments.map {
            val savedFile = fileStorage.saveFile(it)
            Attachment(
                assetId = maintenanceRecord.assetId,
                maintenanceId = maintenanceId,
                fileReference = savedFile.fileReference,
                fileName = savedFile.fileName,
                mimeType = savedFile.mimeType,
                fileSize = savedFile.fileSize
            )
        }
        attachments.forEach {
            attachmentRepository.insertAttachment(it)
        }

    }

}