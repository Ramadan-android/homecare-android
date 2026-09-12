package com.ramadan.homecare.domain.usecase.assetdetails

import com.ramadan.homecare.domain.repository.AssetRepository
import com.ramadan.homecare.domain.repository.AttachmentRepository
import com.ramadan.homecare.domain.repository.MaintenanceRecordRepository
import javax.inject.Inject

class DeleteAssetUseCase @Inject constructor(
    private val assetRepository: AssetRepository,
    private val maintenanceRecordRepository: MaintenanceRecordRepository,
    private val attachmentRepository: AttachmentRepository
) {
    suspend operator fun invoke(assetId: Long){
        assetRepository.deleteAssetById(assetId)
        maintenanceRecordRepository.deleteMaintenanceByAssetId(assetId = assetId)
        attachmentRepository.deleteAttachmentsByAssetId(assetId = assetId)
    }
}