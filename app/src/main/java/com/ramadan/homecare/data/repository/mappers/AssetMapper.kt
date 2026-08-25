package com.ramadan.homecare.data.repository.mappers

import com.ramadan.homecare.data.local.entity.AssetEntity
import com.ramadan.homecare.domain.model.Asset

fun AssetEntity.toAsset(): Asset =
    Asset(
        assetId = this.assetId,
        assetName = this.assetName,
        category = this.category,
        brand = this.brand,
        model = this.model,
        serialNumber = this.serialNumber,
        purchaseDate = this.purchaseDate,
        hasWarranty = this.hasWarranty,
        warrantyExpires = this.warrantyExpires,
        trackMaintenance = this.trackMaintenance,
        intervalMonths = this.intervalMonths,
        notes = this.notes,
        assetPhoto = this.assetPhoto
    )

fun Asset.toEntity(): AssetEntity =
    AssetEntity(
        assetId = this.assetId,
        assetName = this.assetName,
        category = this.category,
        brand = this.brand,
        model = this.model,
        serialNumber = this.serialNumber,
        purchaseDate = this.purchaseDate,
        hasWarranty = this.hasWarranty,
        warrantyExpires = this.warrantyExpires,
        trackMaintenance = this.trackMaintenance,
        intervalMonths = this.intervalMonths,
        notes = this.notes,
        assetPhoto = this.assetPhoto
    )