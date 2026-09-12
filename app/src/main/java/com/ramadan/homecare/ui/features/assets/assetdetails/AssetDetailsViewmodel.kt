package com.ramadan.homecare.ui.features.assets.assetdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ramadan.homecare.domain.usecase.assetdetails.DeleteAssetUseCase
import com.ramadan.homecare.domain.usecase.assetdetails.GetAssetDetailsUseCase
import com.ramadan.homecare.domain.usecase.assetdetails.GetAttachmentUseCase
import com.ramadan.homecare.domain.usecase.assetdetails.GetMaintenanceRecordUseCase
import com.ramadan.homecare.route.AssetDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AssetDetailsViewmodel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAssetDetailsUseCase: GetAssetDetailsUseCase,
    private val getMaintenanceRecordUseCase: GetMaintenanceRecordUseCase,
    private val getAttachmentUseCase: GetAttachmentUseCase,
    private val deleteAssetUseCase: DeleteAssetUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AssetDetailsUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<AssetDetailsEffectEvents>()
    val event = _event.asSharedFlow()


    init {
        val route = savedStateHandle.toRoute<AssetDetailsRoute>()
        val assetId = route.assetId
        getAssetDetails(assetId)

    }


    private fun getAssetDetails(assetId: Long) {
        viewModelScope.launch {
            val asset = getAssetDetailsUseCase(assetId)
            if (asset != null) {
                val attachments = getAttachmentUseCase(assetId)
                getMaintenanceRecordUseCase(assetId).collect { maintenanceRecord ->
                    val latestMaintenanceRecord = maintenanceRecord.maxByOrNull { it.date }


                    val nextMaintenanceDate: LocalDate? = if (asset.intervalMonths != null) {
                        latestMaintenanceRecord?.date?.plusMonths(asset.intervalMonths.toLong())
                            ?: asset.purchaseDate?.plusMonths(asset.intervalMonths.toLong())
                    } else null


                    _uiState.update {
                        it.copy(
                            assetId = asset.assetId,
                            assetPhoto = asset.assetPhoto,
                            assetName = asset.assetName,
                            serialNumber = asset.serialNumber,
                            category = asset.category,
                            brand = asset.brand,
                            model = asset.model,
                            purchaseDate = asset.purchaseDate,
                            warrantyExpires = asset.warrantyExpires,
                            nextMaintenanceDate = nextMaintenanceDate,
                            maintenanceHistory = maintenanceRecord,
                            attachments = attachments,
                            isLoading = false

                        )
                    }
                }
            } else _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun onEvent(event: AssetDetailsEvents){
        when(event){
            is AssetDetailsEvents.OpenAttachment -> TODO()
            is AssetDetailsEvents.DeleteAsset -> {
                viewModelScope.launch {
                    deleteAssetUseCase(event.assetId)
                    showDeleteDialogDismiss()
                    _event.emit(AssetDetailsEffectEvents.NavigateBack)
                }
            }
        }
    }

    fun navigateToEditScreen(){
        viewModelScope.launch {
            _event.emit(AssetDetailsEffectEvents.NavigateToEditAsset(uiState.value.assetId))
        }
    }
    fun navigateToAddMaintenanceScreen(){
        viewModelScope.launch {
            _event.emit(AssetDetailsEffectEvents.AddMaintenanceRecord(uiState.value.assetId))
        }
    }

    fun showDeleteDialogDismiss(){
        _uiState.update {
            it.copy(
                showDeleteDialog = !it.showDeleteDialog
            )
        }
    }
    fun navigateBack(){
        viewModelScope.launch {
            _event.emit(AssetDetailsEffectEvents.NavigateBack)
        }
    }

}