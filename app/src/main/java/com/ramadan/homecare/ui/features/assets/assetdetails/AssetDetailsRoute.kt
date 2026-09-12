package com.ramadan.homecare.ui.features.assets.assetdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramadan.homecare.route.AssetDetailsRoute
import com.ramadan.homecare.ui.features.assets.addasset.toEditAssetScreen

fun NavGraphBuilder.assetDetailsRoute(
    navController: NavController
) {
    composable<AssetDetailsRoute> {navBackStackEntry ->
        AssetDetailsScreen(
            navigateBack = {
                navController.popBackStack()
            },
            navigateToEditScreen = {
                navController.toEditAssetScreen(it)
            },
            navigateToAddMaintenanceScreen = {

            }
        )
    }
}

fun NavController.toAssetDetailsScreen(assetId: Long) {
    navigate(AssetDetailsRoute(assetId))
}