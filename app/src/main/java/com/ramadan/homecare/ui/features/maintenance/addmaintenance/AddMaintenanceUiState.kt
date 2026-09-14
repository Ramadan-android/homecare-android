package com.ramadan.homecare.ui.features.maintenance.addmaintenance

import com.ramadan.homecare.core.util.MaintenanceType
import java.time.LocalDate

data class AddMaintenanceUiState(
    val assetId: Long = 0,
    val maintenanceType: MaintenanceType = MaintenanceType.All,
    val maintenanceTypeMenuVisible: Boolean = false,
    val maintenanceTypeMenuErrorMessage: String? = null,
    val date: LocalDate? = null,
    val showDatePicker: Boolean = false,
    val dateErrorMessage: String? = null,
    val cost: String? = null,
    val costErrorMessage: String? = null,
    val serviceProvider: String? = null,
    val notes: String? = null,
    val isLoading: Boolean = false,
    val attachments: List<String> = emptyList(),
) {
    val buttonEnabled =
        maintenanceType != MaintenanceType.All &&
                date != null &&
                cost != null && !isLoading


}


