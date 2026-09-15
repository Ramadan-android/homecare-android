package com.ramadan.homecare.data.repository

import com.ramadan.homecare.data.local.dao.MaintenanceRecordDao
import com.ramadan.homecare.data.repository.mappers.toEntity
import com.ramadan.homecare.data.repository.mappers.toMaintenanceRecord
import com.ramadan.homecare.domain.model.MaintenanceRecord
import com.ramadan.homecare.domain.repository.MaintenanceRecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MaintenanceRecordRepositoryImpl @Inject constructor(
    private val maintenanceRecordDao: MaintenanceRecordDao
): MaintenanceRecordRepository {
    override suspend fun insertMaintenance(maintenance: MaintenanceRecord): Long {
        return maintenanceRecordDao.insertMaintenance(maintenance.toEntity())
    }

    override suspend fun getMaintenanceRecords(): List<MaintenanceRecord> {
        return maintenanceRecordDao.getMaintenanceRecords().map { it.toMaintenanceRecord() }
    }

    override fun getMaintenanceRecordsByAssetId(assetId: Long): Flow<List<MaintenanceRecord>> {
        return maintenanceRecordDao.getMaintenanceRecordsByAssetId(assetId)
            .map {entities ->
                entities.map { it.toMaintenanceRecord() }
            }
    }

    override suspend fun getMaintenanceRecordById(maintenanceId: Long): MaintenanceRecord? {
        return maintenanceRecordDao.getMaintenanceRecordById(maintenanceId)?.toMaintenanceRecord()
    }

    override suspend fun deleteMaintenanceById(maintenanceId: Long) {
        maintenanceRecordDao.deleteMaintenanceById(maintenanceId)
    }

    override suspend fun deleteMaintenanceByAssetId(assetId: Long) {
        maintenanceRecordDao.deleteMaintenanceByAssetId(assetId)
    }

}