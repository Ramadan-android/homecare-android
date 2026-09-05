package com.ramadan.homecare.ui.features.assets.addasset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.domain.model.Asset
import com.ramadan.homecare.domain.usecase.AddAssetResult
import com.ramadan.homecare.domain.usecase.AddAssetUseCase
import com.ramadan.homecare.domain.usecase.AddAssetValidationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAssetViewModel @Inject constructor(
    private val addAssetUseCase: AddAssetUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AddAssetUiState())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<AddAssetUiEffectEvent>()
    val event = _event.asSharedFlow()

    fun onEvent(event: AddAssetUiEvent) {

        when (event) {
            is AddAssetUiEvent.AssetCategoryChanged -> {
                _state.update {
                    it.copy(
                        category = event.category,
                        assetCategoryError = if (it.assetCategoryError != null)
                            if (event.category == AssetCategory.All) "category field cannot be blank" else null
                        else null
                    )
                }
            }

            is AddAssetUiEvent.AssetNameChanged -> {
                _state.update {
                    it.copy(
                        assetName = event.assetName,
                        assetNameError = if (it.assetNameError != null)
                            if (event.assetName.isBlank()) "Asset name cannot be blank" else null
                        else null,
                    )
                }
            }

            is AddAssetUiEvent.BrandChanged -> {
                _state.update {
                    it.copy(
                        brand = event.brand,
                        brandError = if (it.brandError != null)
                            if (event.brand.isBlank()) "Brand cannot be blank" else null
                        else null
                    )
                }
            }

            is AddAssetUiEvent.IntervalMonthsChanged -> {
                _state.update { it.copy(intervalMonths = event.month) }
            }

            is AddAssetUiEvent.ModelChanged -> {
                _state.update {
                    it.copy(
                        model = event.model,
                        modelError = if (it.modelError != null)
                            if (event.model.isBlank()) "Model cannot be blank" else null
                        else null
                    )
                }
            }

            is AddAssetUiEvent.NotesChanged -> {
                _state.update { it.copy(notes = event.notes) }
            }

            is AddAssetUiEvent.SerialNumberChanged -> {
                _state.update { it.copy(serialNumber = event.serialNumber) }
            }

            AddAssetUiEvent.TrackMaintenanceChanged -> {
                _state.update {
                    it.copy(
                        trackMaintenance = !it.trackMaintenance,
                        intervalMonths = if (it.trackMaintenance) null else it.intervalMonths

                    )
                }
            }

            AddAssetUiEvent.WarrantyChanged -> {
                _state.update {
                    it.copy(
                        hasWarranty = !it.hasWarranty,
                        warrantyExpires = if (it.hasWarranty) null else it.warrantyExpires

                    )
                }
            }

            is AddAssetUiEvent.PickDate -> {
                _state.update {
                    when (event.dateType) {
                        DateType.PURCHASE_DATE -> {
                            it.copy(purchaseDate = event.date)
                        }

                        DateType.WARRANTY_EXPIRES -> {
                            it.copy(warrantyExpires = event.date)
                        }
                    }
                }
            }

            is AddAssetUiEvent.PickPhoto -> {
                _state.update { it.copy(assetPhoto = event.photo) }
            }

            AddAssetUiEvent.SaveAsset -> {

                val currentState = _state.value
                val assetCategoryError =
                    if (currentState.category == AssetCategory.All) {
                        "category field cannot be blank"
                    } else {
                        null
                    }

                val assetNameError =
                    if (currentState.assetName.isBlank()) {
                        "Asset name cannot be blank"
                    } else {
                        null
                    }

                val brandError =
                    if (currentState.brand.isBlank()) {
                        "Brand cannot be blank"
                    } else {
                        null
                    }

                val modelError =
                    if (currentState.model.isBlank()) {
                        "Model cannot be blank"
                    } else {
                        null
                    }

                _state.update {
                    it.copy(
                        assetCategoryError = assetCategoryError,
                        assetNameError = assetNameError,
                        brandError = brandError,
                        modelError = modelError
                    )
                }

                if (
                    assetCategoryError != null ||
                    assetNameError != null ||
                    brandError != null ||
                    modelError != null

                ) {
                    return
                }


                viewModelScope.launch {
                    val asset = Asset(
                        assetName = currentState.assetName,
                        category = currentState.category,
                        brand = currentState.brand,
                        model = currentState.model,
                        serialNumber = currentState.serialNumber,
                        purchaseDate = currentState.purchaseDate,
                        hasWarranty = currentState.hasWarranty,
                        warrantyExpires = currentState.warrantyExpires,
                        trackMaintenance = currentState.trackMaintenance,
                        intervalMonths = currentState.intervalMonths,
                        notes = currentState.notes,
                        assetPhoto = currentState.assetPhoto
                    )

                    when(val addAssetResult = addAssetUseCase.addAsset(asset)){
                        AddAssetResult.Success -> {
                            _state.update { it.copy(isLoading = false) }

                            _event.emit(
                                AddAssetUiEffectEvent.NavigateToMyAssets
                            )
                        }
                        is AddAssetResult.ValidationError -> {

                            _state.update {
                                it.copy(
                                    warrantyExpiresError = if (AddAssetValidationError.WARRANTY_EXPIRES_REQUIRED in addAssetResult.errors)
                                        "Warranty expires date is required"
                                    else null,
                                    intervalMonthsError = if (AddAssetValidationError.INTERVAL_MONTHS_REQUIRED in addAssetResult.errors)
                                        "Interval months is required"
                                    else null,
                                    isLoading = false

                                )
                            }

                        }
                    }

                }
            }

            is AddAssetUiEvent.CategoryMenuIconClicked -> {
                _state.update { it.copy(isVisibleCategoryMenu = event.isVisible) }
            }

            AddAssetUiEvent.DatePickerIconClicked -> {
                _state.update { it.copy(isVisibleDatePicker = !it.isVisibleDatePicker) }
            }

            is AddAssetUiEvent.MonthsMenuIconClicked -> {
                _state.update { it.copy(isVisibleMonthsMenu = event.isVisible) }

            }

            AddAssetUiEvent.WarrantyDatePickerIconClicked -> {
                _state.update { it.copy(isVisibleWarrantyDatePicker = !it.isVisibleWarrantyDatePicker) }
            }
        }
    }

    fun navigateToMyAssets() {
        viewModelScope.launch {
            _event.emit(AddAssetUiEffectEvent.NavigateToMyAssets)
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _event.emit(AddAssetUiEffectEvent.NavigateBack)
        }
    }
}