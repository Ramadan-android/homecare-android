package com.ramadan.homecare.ui.features.maintenance.addmaintenance

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ramadan.homecare.core.util.MaintenanceType
import com.ramadan.homecare.domain.model.MaintenanceRecord
import com.ramadan.homecare.domain.usecase.maintenance.AddMaintenanceUseCase
import com.ramadan.homecare.route.AddMaintenanceRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMaintenanceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addMaintenanceUseCase: AddMaintenanceUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AddMaintenanceUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<AddMaintenanceUiEffectEvent>()
    val event = _event.asSharedFlow()

    init {
        val assetId = savedStateHandle.toRoute<AddMaintenanceRoute>().assetId
        _state.update {
            it.copy(assetId = assetId)
        }
    }

    fun onEvent(event: AddMaintenanceUiEvent) {
        when (event) {
            is AddMaintenanceUiEvent.AddAttachmentClicked -> {
                _state.update {
                    it.copy(
                        attachments = event.attachments,
                    )
                }

            }

            is AddMaintenanceUiEvent.CostChanged -> {

                _state.update {
                    it.copy(
                        cost = event.cost,
                        costErrorMessage = null
                    )
                }


            }

            is AddMaintenanceUiEvent.DateChanged -> {
                _state.update {
                    it.copy(date = event.date)
                }
            }

            is AddMaintenanceUiEvent.MaintenanceTypeChanged -> {
                _state.update {
                    it.copy(maintenanceType = event.maintenanceType)
                }
            }

            AddMaintenanceUiEvent.SaveMaintenanceRecordClicked -> {

                val currentState = _state.value
                val maintenanceTypeError =
                    if (currentState.maintenanceType == MaintenanceType.All) {
                        "Maintenance type is required"
                    } else null
                val dateErrorMessage =
                    if (currentState.date == null) {
                        "Date is required"
                    } else null
                val costErrorMessage = try {
                    currentState.cost?.toLong()
                    null
                } catch (_: Exception) {
                    "Invalid cost"
                }
                _state.update {
                    it.copy(
                        maintenanceTypeMenuErrorMessage = maintenanceTypeError,
                        dateErrorMessage = dateErrorMessage,
                        costErrorMessage = costErrorMessage
                    )
                }
                if (
                    maintenanceTypeError != null ||
                    dateErrorMessage != null ||
                    costErrorMessage != null
                ) return
                _state.update {
                    it.copy(isLoading = true)
                }
                viewModelScope.launch {
                    val maintenanceRecord = MaintenanceRecord(
                        assetId = currentState.assetId,
                        maintenanceType = currentState.maintenanceType,
                        date = currentState.date!!,
                        cost = currentState.cost?.toLong() ?: 0,
                        serviceProvider = currentState.serviceProvider,
                        notes = currentState.notes
                    )
                    addMaintenanceUseCase(maintenanceRecord, currentState.attachments)
                    _event.emit(AddMaintenanceUiEffectEvent.NavigateBack)
                }


            }

            is AddMaintenanceUiEvent.ServiceProviderChanged -> {
                _state.update {
                    it.copy(serviceProvider = event.serviceProvider)
                }
            }

            AddMaintenanceUiEvent.ToggleDatePicker -> {
                _state.update {
                    it.copy(showDatePicker = !it.showDatePicker)
                }
            }

            is AddMaintenanceUiEvent.NotesChanged -> {
                _state.update {
                    it.copy(notes = event.notes)
                }
            }

            AddMaintenanceUiEvent.MaintenanceTypeMenuIconClicked -> {
                _state.update {
                    it.copy(maintenanceTypeMenuVisible = !it.maintenanceTypeMenuVisible)
                }

            }
        }
    }
}