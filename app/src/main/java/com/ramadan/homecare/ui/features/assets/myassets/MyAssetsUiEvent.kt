package com.ramadan.homecare.ui.features.assets.myassets

import com.ramadan.homecare.core.util.AssetCategory

sealed interface MyAssetsUiEvent {
    data class SearchQueryChanged(val query: String): MyAssetsUiEvent
    data class CategoryChanged(val category: AssetCategory): MyAssetsUiEvent
}

sealed interface MyAssetsUiEffectEvent {
    data object NavigateToAddAsset: MyAssetsUiEffectEvent
    data class NavigateToAssetDetails(val assetId: Long): MyAssetsUiEffectEvent
}