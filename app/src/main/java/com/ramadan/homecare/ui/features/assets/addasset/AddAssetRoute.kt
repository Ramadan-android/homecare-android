package com.ramadan.homecare.ui.features.assets.addasset

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramadan.homecare.route.AddEditAssetRoute
import com.ramadan.homecare.ui.features.assets.myassets.toMyAsset

fun NavGraphBuilder.addAssetRoute(
    navController: NavController
) {
    composable<AddEditAssetRoute> {
        AddAssetScreen(
            navigateBack = navController::popBackStack,
            navigateToMyAssets = navController::toMyAsset
        )
    }
}

fun NavController.toAddAssetScreen() {
    navigate(AddEditAssetRoute(null))
}


fun NavController.toEditAssetScreen(assetId: Long) {
    navigate(AddEditAssetRoute(assetId))
}