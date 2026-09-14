package com.ramadan.homecare.route

import kotlinx.serialization.Serializable

@Serializable
data object MyAssetRoute

@Serializable
data class AddEditAssetRoute(val assetId: Long?)

@Serializable
data class AssetDetailsRoute(val assetId: Long)

@Serializable
data class AddMaintenanceRoute(val assetId: Long)