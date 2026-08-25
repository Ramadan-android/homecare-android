package com.ramadan.homecare.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.ramadan.homecare.core.util.Constants
import com.ramadan.homecare.data.local.entity.AssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: AssetEntity)

    @Query("SELECT * FROM ${Constants.ASSETS_TABLE}")
    fun getAssets(): Flow<List<AssetEntity>>

    @Query("SELECT * FROM ${Constants.ASSETS_TABLE} WHERE assetId = :assetId")
    suspend fun getAssetById(assetId: Long): AssetEntity?

    @Query("DELETE FROM ${Constants.ASSETS_TABLE} WHERE assetId = :assetId")
    suspend fun deleteAssetById(assetId: Long)
}