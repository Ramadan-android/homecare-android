package com.ramadan.homecare.domain.usecase.assetdetails

import com.ramadan.homecare.domain.model.Asset
import com.ramadan.homecare.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetDetailsUseCase @Inject constructor(
    private val assetRepository: AssetRepository,
){
    suspend operator fun invoke(assetId: Long): Asset? = assetRepository.getAssetById(assetId)


}