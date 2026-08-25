package com.ramadan.homecare.data.local.typeConverters
import androidx.room3.ColumnTypeConverter
import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.core.util.MaintenanceType

class EnumTypeConverter {

//    AssetCategory
    @ColumnTypeConverter
    fun assetCategoryToString(assetCategory: AssetCategory): String = assetCategory.name

    @ColumnTypeConverter
    fun stringToAssetCategory(assetCategory: String): AssetCategory = AssetCategory.valueOf(assetCategory)

//    MaintenanceType
    @ColumnTypeConverter
    fun maintenanceTypeToString(maintenanceType: MaintenanceType): String = maintenanceType.name

    @ColumnTypeConverter
    fun stringToMaintenanceType(maintenanceType: String): MaintenanceType = MaintenanceType.valueOf(maintenanceType)


}