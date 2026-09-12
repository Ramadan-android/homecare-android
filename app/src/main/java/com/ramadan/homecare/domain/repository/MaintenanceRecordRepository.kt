package com.ramadan.homecare.domain.repository

import com.ramadan.homecare.domain.model.MaintenanceRecord
import kotlinx.coroutines.flow.Flow

interface MaintenanceRecordRepository {

    suspend fun insertMaintenance(maintenance: MaintenanceRecord)

    fun getMaintenanceRecordsByAssetId(assetId: Long): Flow<List<MaintenanceRecord>>

    suspend fun getMaintenanceRecordById(maintenanceId: Long): MaintenanceRecord?

    suspend fun deleteMaintenanceById(maintenanceId: Long)

    suspend fun deleteMaintenanceByAssetId(assetId: Long)

}