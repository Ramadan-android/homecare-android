package com.ramadan.homecare.ui.features.maintenance.addmaintenance

import com.ramadan.homecare.core.util.MaintenanceType
import java.time.LocalDate

sealed interface AddMaintenanceUiEvent {
    data class MaintenanceTypeChanged(val maintenanceType: MaintenanceType) : AddMaintenanceUiEvent
    data object MaintenanceTypeMenuIconClicked: AddMaintenanceUiEvent
    data object ToggleDatePicker : AddMaintenanceUiEvent
    data class DateChanged(val date: LocalDate) : AddMaintenanceUiEvent
    data class CostChanged(val cost: String) : AddMaintenanceUiEvent
    data class ServiceProviderChanged(val serviceProvider: String) : AddMaintenanceUiEvent
    data class NotesChanged(val notes: String) : AddMaintenanceUiEvent
    data class AddAttachmentClicked(val attachments: List<String>) : AddMaintenanceUiEvent
    data object SaveMaintenanceRecordClicked : AddMaintenanceUiEvent

}

sealed interface AddMaintenanceUiEffectEvent{
    data object NavigateBack: AddMaintenanceUiEffectEvent
}