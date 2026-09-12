package com.ramadan.homecare.ui.features.assets.addasset

import com.ramadan.homecare.core.util.AssetCategory
import java.time.LocalDate

data class AddAssetUiState(
    val assetEditId: Long? = null,
    val assetName: String = "",
    val category: AssetCategory = AssetCategory.All,
    val brand: String = "",
    val model: String = "",
    val serialNumber: String = "",
    val purchaseDate: LocalDate? = null,
    val hasWarranty: Boolean = false,
    val warrantyExpires: LocalDate? = null,
    val trackMaintenance: Boolean = false,
    val intervalMonths: Int? = null,
    val notes: String = "",
    val screenMode: ScreenMode = ScreenMode.Add,
    val errorMessage: String? = null,
    val assetCategoryError: String? = null,
    val assetNameError: String? = null,
    val brandError: String? = null,
    val modelError: String? = null,
    val assetPhoto: String? = null,
    val warrantyExpiresError : String? = null,
    val intervalMonthsError : String? = null,
    val isLoading: Boolean = false,
    val isVisibleDatePicker: Boolean = false,
    val isVisibleWarrantyDatePicker: Boolean = false,
    val isVisibleCategoryMenu: Boolean = false,
    val isVisibleMonthsMenu: Boolean = false
)

enum class ScreenMode{
    Add,
    Edit
}