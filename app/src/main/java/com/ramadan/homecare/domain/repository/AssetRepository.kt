package com.ramadan.homecare.domain.repository

import com.ramadan.homecare.domain.model.Asset
import kotlinx.coroutines.flow.Flow

interface AssetRepository {

    suspend fun insertAsset(asset: Asset)

    fun getAssets(): Flow<List<Asset>>

    suspend fun getAssetById(assetId: Long): Asset?

    suspend fun deleteAssetById(assetId: Long)
}
