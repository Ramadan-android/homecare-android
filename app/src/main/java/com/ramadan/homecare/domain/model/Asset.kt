package com.ramadan.homecare.domain.model

import com.ramadan.homecare.core.util.AssetCategory
import java.time.LocalDate

data class Asset(
    val assetId: Long,
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
