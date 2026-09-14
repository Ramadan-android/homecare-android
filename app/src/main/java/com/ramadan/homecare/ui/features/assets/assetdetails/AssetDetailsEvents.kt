package com.ramadan.homecare.ui.features.assets.assetdetails


sealed class AssetDetailsEvents {
    data class DeleteAsset(val assetId: Long) : AssetDetailsEvents()


}
sealed class AssetDetailsEffectEvents {

    data object NavigateBack : AssetDetailsEffectEvents()
    data class NavigateToEditAsset(val assetId: Long) : AssetDetailsEffectEvents()
    data class AddMaintenanceRecord(val assetId: Long) : AssetDetailsEffectEvents()
//    data class OpenAttachment(val fileReference: String): AssetDetailsEffectEvents()

}