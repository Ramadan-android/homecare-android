package com.ramadan.homecare.domain.usecase.asset

import com.ramadan.homecare.domain.model.MaintenanceRecord
import com.ramadan.homecare.domain.repository.MaintenanceRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMaintenanceRecordUseCase @Inject constructor(
    private val maintenanceRecordRepository: MaintenanceRecordRepository,

    ) {

    operator fun invoke(assetId: Long): Flow<List<MaintenanceRecord>> =
        maintenanceRecordRepository.getMaintenanceRecordsByAssetId(assetId)

}