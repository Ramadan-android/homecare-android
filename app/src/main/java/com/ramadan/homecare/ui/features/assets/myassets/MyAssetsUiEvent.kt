package com.ramadan.homecare.ui.features.assets.myassets

import com.ramadan.homecare.core.util.AssetCategory

sealed interface MyAssetsUiEvent {
    data class SearchQueryChanged(val query: String): MyAssetsUiEvent
    data class CategoryChanged(val category: AssetCategory): MyAssetsUiEvent
    data class AssetClicked(val assetId: Long): MyAssetsUiEvent
    data object AddAssetClicked: MyAssetsUiEvent
}