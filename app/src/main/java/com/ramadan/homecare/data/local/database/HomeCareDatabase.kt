package com.ramadan.homecare.data.local.database

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.ramadan.homecare.data.local.dao.AssetDao
import com.ramadan.homecare.data.local.dao.AttachmentDao
import com.ramadan.homecare.data.local.dao.MaintenanceRecordDao
import com.ramadan.homecare.data.local.entity.AssetEntity
import com.ramadan.homecare.data.local.entity.AttachmentEntity
import com.ramadan.homecare.data.local.entity.MaintenanceRecordEntity
import com.ramadan.homecare.data.local.typeConverters.EnumTypeConverter
import com.ramadan.homecare.data.local.typeConverters.LocalDateConverter

@Database(entities = [AssetEntity::class, MaintenanceRecordEntity::class, AttachmentEntity::class], version = 1)
@ColumnTypeConverters(EnumTypeConverter::class, LocalDateConverter::class)
abstract class HomeCareDatabase: RoomDatabase(){

    abstract fun assetDao(): AssetDao
    abstract fun maintenanceRecordDao(): MaintenanceRecordDao
    abstract fun attachmentDao(): AttachmentDao
}