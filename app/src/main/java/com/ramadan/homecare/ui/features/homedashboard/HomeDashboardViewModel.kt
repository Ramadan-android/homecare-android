package com.ramadan.homecare.ui.features.homedashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramadan.homecare.domain.model.Asset
import com.ramadan.homecare.domain.model.MaintenanceRecord
import com.ramadan.homecare.domain.usecase.asset.GetAssetsUseCase
import com.ramadan.homecare.domain.usecase.maintenance.GetMaintenanceUseCase
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
class HomeDashboardViewModel @Inject constructor(
    private val getAssetsUseCase: GetAssetsUseCase,
    private val getMaintenanceUseCase: GetMaintenanceUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeDashboardUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeDashboardUiEffectEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {

            val assets = getAssetsUseCase()
            val records = getMaintenanceUseCase()

            assets.collect { assetList ->

                if (assetList.isNotEmpty()) {

                    val today = LocalDate.now()

                    val upcomingMaintenance = assetList
                        .mapNotNull { asset ->

                            val assetRecords = records.filter {
                                it.assetId == asset.assetId
                            }

                            val latestMaintenance =
                                assetRecords.maxByOrNull { it.date }

                            val nextMaintenanceDate =
                                calculateNextMaintenanceDate(
                                    asset = asset,
                                    latestMaintenance = latestMaintenance
                                )

                            if (nextMaintenanceDate != null) {
                                UpcomingMaintenance(
                                    assetId = asset.assetId,
                                    assetName = asset.assetName,
                                    date = nextMaintenanceDate,
                                )
                            } else {
                                null
                            }
                        }
                        .sortedBy { it.date }

                    val maintenanceDue = upcomingMaintenance.count {
                        it.date.isBefore(today) || it.date.isEqual(today)
                    }

                    val upcoming = upcomingMaintenance
                        .filter { it.date.isAfter(today) }
                        .take(5)

                    val warrantyExpiryThreshold = today.plusDays(30)

                    val expiringWarranties = assetList.count { asset ->
                        val warrantyExpires = asset.warrantyExpires

                        warrantyExpires != null &&
                                !warrantyExpires.isBefore(today) &&
                                !warrantyExpires.isAfter(warrantyExpiryThreshold)
                    }

                    val recentAsset =
                        assetList.sortedBy { it.purchaseDate }.last()

                    val recentMaintenance =
                        records
                            .filter { it.assetId == recentAsset.assetId }
                            .maxByOrNull { it.date }

                    _state.update {
                        it.copy(
                            totalAssets = assetList.size.toString(),
                            maintenanceDue = maintenanceDue.toString(),
                            expiringWarranties = expiringWarranties.toString(),
                            upcomingMaintenance = upcoming,
                            recentActivity = RecentActivity(
                                assetName = recentAsset.assetName,
                                purchaseDate = recentAsset.purchaseDate,
                                maintenanceType = recentMaintenance
                                    ?.maintenanceType
                                    ?.name
                                    ?: "no maintenance",
                                maintenanceDate = recentMaintenance?.date
                            )
                        )
                    }
                }
            }
        }
    }

    private fun calculateNextMaintenanceDate(
        asset: Asset,
        latestMaintenance: MaintenanceRecord?
    ): LocalDate? {

        if (!asset.trackMaintenance) {
            return null
        }

        val intervalMonths = asset.intervalMonths
            ?: return null

        return latestMaintenance?.date?.plusMonths(intervalMonths.toLong())
            ?: asset.purchaseDate?.plusMonths(intervalMonths.toLong())
    }

    fun navigateToAddAsset() {
        viewModelScope.launch {
            _event.emit(HomeDashboardUiEffectEvent.AddAsset)
        }
    }
}