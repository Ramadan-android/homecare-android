package com.ramadan.homecare.domain.usecase.maintenance

import com.ramadan.homecare.domain.repository.MaintenanceRecordRepository
import javax.inject.Inject

class GetMaintenanceUseCase @Inject constructor(
    private val maintenanceRecordRepository: MaintenanceRecordRepository
) {
    suspend operator fun invoke() =
        maintenanceRecordRepository.getMaintenanceRecords()

}