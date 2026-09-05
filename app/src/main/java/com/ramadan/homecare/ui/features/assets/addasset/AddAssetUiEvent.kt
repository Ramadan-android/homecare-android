package com.ramadan.homecare.ui.features.assets.addasset

import com.ramadan.homecare.core.util.AssetCategory
import java.time.LocalDate

sealed interface AddAssetUiEvent {
    data class AssetNameChanged(val assetName: String): AddAssetUiEvent
    data class BrandChanged(val brand: String): AddAssetUiEvent
    data class ModelChanged(val model: String): AddAssetUiEvent
    data class SerialNumberChanged(val serialNumber: String): AddAssetUiEvent
    data class NotesChanged(val notes: String): AddAssetUiEvent
    data class AssetCategoryChanged(val category: AssetCategory): AddAssetUiEvent
    data class IntervalMonthsChanged(val month: Int): AddAssetUiEvent
    data object WarrantyChanged: AddAssetUiEvent
    data object TrackMaintenanceChanged: AddAssetUiEvent
    data object DatePickerIconClicked: AddAssetUiEvent
    data object WarrantyDatePickerIconClicked: AddAssetUiEvent
    data class CategoryMenuIconClicked(val isVisible: Boolean): AddAssetUiEvent
    data class MonthsMenuIconClicked(val isVisible: Boolean): AddAssetUiEvent
    data class PickDate(val dateType: DateType, val date: LocalDate): AddAssetUiEvent
    data class PickPhoto(val photo: String): AddAssetUiEvent
    data object SaveAsset: AddAssetUiEvent
}

sealed interface AddAssetUiEffectEvent {
    data object NavigateBack: AddAssetUiEffectEvent
    data object NavigateToMyAssets: AddAssetUiEffectEvent
}

enum class DateType {
    PURCHASE_DATE,
    WARRANTY_EXPIRES
}