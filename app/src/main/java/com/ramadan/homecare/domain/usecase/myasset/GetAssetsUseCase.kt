package com.ramadan.homecare.domain.usecase.myasset

import com.ramadan.homecare.domain.model.Asset
import com.ramadan.homecare.domain.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAssetsUseCase @Inject constructor(
    private val assetRepository: AssetRepository
){
    fun getAssets(): Flow<List<Asset>> {
        return assetRepository.getAssets()
    }
    operator fun invoke() = getAssets()
}