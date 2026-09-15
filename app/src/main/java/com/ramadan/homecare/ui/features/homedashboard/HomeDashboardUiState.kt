package com.ramadan.homecare.ui.features.homedashboard

import com.ramadan.homecare.domain.model.MaintenanceRecord
import java.time.LocalDate

data class HomeDashboardUiState(
    val totalAssets: String = "",
    val maintenanceDue: String = "",
    val expiringWarranties: String = "",
    val upcomingMaintenance: List<UpcomingMaintenance> = emptyList(),
    val recentActivity: RecentActivity = RecentActivity()
)
data class UpcomingMaintenance(
    val assetId: Long,
    val assetName: String,
    val date: LocalDate,
)
data class RecentActivity(
    val assetName: String = "",
    val purchaseDate: LocalDate? = null,
    val maintenanceType: String = "",
    val maintenanceDate: LocalDate? = null
)