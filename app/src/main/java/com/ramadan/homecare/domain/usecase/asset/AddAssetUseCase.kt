package com.ramadan.homecare.domain.usecase.asset

import com.ramadan.homecare.domain.storage.FileStorage
import com.ramadan.homecare.domain.model.Asset
import com.ramadan.homecare.domain.repository.AssetRepository
import javax.inject.Inject

class AddAssetUseCase @Inject constructor(
    private val assetRepository: AssetRepository,
    private val fileStorage: FileStorage
) {

    suspend fun addAsset(asset: Asset): AddAssetResult {


        val errors = buildSet {

            if (asset.hasWarranty && asset.warrantyExpires == null) {
                add(AddAssetValidationError.WARRANTY_EXPIRES_REQUIRED)
            }

            if (
                asset.trackMaintenance &&
                (asset.intervalMonths == null || asset.intervalMonths < 1)
            ) {
                add(AddAssetValidationError.INTERVAL_MONTHS_REQUIRED)
            }
        }

        if (errors.isNotEmpty()) {
            return AddAssetResult.ValidationError(errors)
        }

        val validAsset = if (asset.assetPhoto != null) {
            asset.copy(
                assetPhoto = fileStorage.saveFile(asset.assetPhoto).fileReference
            )
        }else asset

        assetRepository.insertAsset(validAsset)

        return AddAssetResult.Success
    }
}

sealed interface AddAssetResult {

    data object Success : AddAssetResult

    data class ValidationError(
        val errors: Set<AddAssetValidationError>
    ) : AddAssetResult
}

enum class AddAssetValidationError {
    WARRANTY_EXPIRES_REQUIRED,
    INTERVAL_MONTHS_REQUIRED
}