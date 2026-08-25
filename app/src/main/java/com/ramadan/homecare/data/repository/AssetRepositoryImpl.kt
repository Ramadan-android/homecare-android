package com.ramadan.homecare.data.repository

import com.ramadan.homecare.data.local.dao.AssetDao
import com.ramadan.homecare.data.repository.mappers.toAsset
import com.ramadan.homecare.data.repository.mappers.toEntity
import com.ramadan.homecare.domain.model.Asset
import com.ramadan.homecare.domain.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(
    private val assetDao: AssetDao
): AssetRepository {
    override suspend fun insertAsset(asset: Asset) {
        assetDao.insertAsset(asset.toEntity())
    }

    override fun getAssets(): Flow<List<Asset>> {
        return assetDao.getAssets()
            .map {entities ->
                entities.map { it.toAsset() }
            }
    }

    override suspend fun getAssetById(assetId: Long): Asset? {
        return assetDao.getAssetById(assetId)?.toAsset()
    }

    override suspend fun deleteAssetById(assetId: Long) {
        assetDao.deleteAssetById(assetId)
    }
}