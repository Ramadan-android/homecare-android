package com.ramadan.homecare.data.repository.mappers

import com.ramadan.homecare.data.local.entity.MaintenanceRecordEntity
import com.ramadan.homecare.domain.model.MaintenanceRecord

fun MaintenanceRecordEntity.toMaintenanceRecord(): MaintenanceRecord =
    MaintenanceRecord(
        id = this.id,
        assetId = this.assetId,
        maintenanceType = this.maintenanceType,
        date = this.date,
        cost = this.cost,
        serviceProvider = this.serviceProvider,
        notes = this.notes
    )

fun MaintenanceRecord.toEntity(): MaintenanceRecordEntity =
    MaintenanceRecordEntity(
        id = this.id,
        assetId = this.assetId,
        maintenanceType = this.maintenanceType,
        date = this.date,
        cost = this.cost,
        serviceProvider = this.serviceProvider,
        notes = this.notes
    )