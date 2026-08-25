package com.ramadan.homecare.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.core.util.Constants
import java.time.LocalDate

@Entity(tableName = Constants.ASSETS_TABLE)
data class AssetEntity(
    @PrimaryKey(autoGenerate = true) val assetId: Long,
    val assetName: String,
    val category: AssetCategory,
    val brand: String,
    val model: String,
    val serialNumber: String?,
    val purchaseDate: LocalDate?,
    val hasWarranty: Boolean,
    val warrantyExpires: LocalDate?,
    val trackMaintenance: Boolean,
    val intervalMonths: Int?,
    val notes: String?,
    val assetPhoto: String?,
)
