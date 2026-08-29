package com.ramadan.homecare.ui.features.assets.myassets

import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.domain.model.Asset

data class MyAssetsUiState (
    val myAssets: List<Asset> = emptyList(),
    val query: String = "",
    val selectedCategory: AssetCategory = AssetCategory.All,
    val isLoading: Boolean = false,
)