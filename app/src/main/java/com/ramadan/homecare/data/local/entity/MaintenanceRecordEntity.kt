package com.ramadan.homecare.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.ramadan.homecare.core.util.Constants
import com.ramadan.homecare.core.util.MaintenanceType
import java.time.LocalDate

@Entity(tableName = Constants.MAINTENANCE_RECORDS_TABLE)
data class MaintenanceRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val assetId: Long,
    val maintenanceType: MaintenanceType,
    val date: LocalDate,
    val cost: Long,
    val serviceProvider: String?,
    val notes: String?
)
