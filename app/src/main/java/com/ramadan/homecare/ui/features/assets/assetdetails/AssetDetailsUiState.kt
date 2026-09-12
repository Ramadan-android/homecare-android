package com.ramadan.homecare.ui.features.assets.assetdetails

import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.domain.model.Attachment
import com.ramadan.homecare.domain.model.MaintenanceRecord
import java.time.LocalDate

data class AssetDetailsUiState(
    val assetId: Long = 0L,
    val assetPhoto: String? = null,
    val assetName: String = "",
    val serialNumber: String? = null,
    val category: AssetCategory = AssetCategory.OTHER,
    val brand: String = "",
    val model: String = "",
    val purchaseDate: LocalDate? = null,
    val warrantyExpires: LocalDate? = null,
    val nextMaintenanceDate: LocalDate? = null,
    val maintenanceHistory: List<MaintenanceRecord> = emptyList(),
    val attachments: List<Attachment> = emptyList(),
    val isLoading: Boolean = false,
    val showDeleteDialog: Boolean = false


)
