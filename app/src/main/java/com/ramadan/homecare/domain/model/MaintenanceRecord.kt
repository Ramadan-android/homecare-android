package com.ramadan.homecare.domain.model

import com.ramadan.homecare.core.util.MaintenanceType
import java.time.LocalDate

data class MaintenanceRecord(
    val id: Long = 0L,
    val assetId: Long,
    val maintenanceType: MaintenanceType,
    val date: LocalDate,
    val cost: Long,
    val serviceProvider: String?,
    val notes: String?
)
