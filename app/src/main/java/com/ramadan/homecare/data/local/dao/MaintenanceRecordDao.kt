package com.ramadan.homecare.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.ramadan.homecare.core.util.Constants
import com.ramadan.homecare.data.local.entity.MaintenanceRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaintenance(maintenance: MaintenanceRecordEntity): Long

    @Query("SELECT * FROM ${Constants.MAINTENANCE_RECORDS_TABLE}")
    suspend fun getMaintenanceRecords(): List<MaintenanceRecordEntity>

    @Query("SELECT * FROM ${Constants.MAINTENANCE_RECORDS_TABLE} WHERE assetId = :assetId")
    fun getMaintenanceRecordsByAssetId(assetId: Long): Flow<List<MaintenanceRecordEntity>>

    @Query("SELECT * FROM ${Constants.MAINTENANCE_RECORDS_TABLE} WHERE id = :maintenanceId")
    suspend fun getMaintenanceRecordById(maintenanceId: Long): MaintenanceRecordEntity?

    @Query("DELETE FROM ${Constants.MAINTENANCE_RECORDS_TABLE} WHERE id = :maintenanceId")
    suspend fun deleteMaintenanceById(maintenanceId: Long)

    @Query("DELETE FROM ${Constants.MAINTENANCE_RECORDS_TABLE} WHERE assetId = :assetId")
    suspend fun deleteMaintenanceByAssetId(assetId: Long)
}