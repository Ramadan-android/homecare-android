package com.ramadan.homecare.ui.features.assets.myassets

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramadan.homecare.route.MyAssetRoute
import com.ramadan.homecare.ui.features.assets.addasset.toAddAssetScreen
import com.ramadan.homecare.ui.features.assets.assetdetails.toAssetDetailsScreen

fun NavGraphBuilder.myAssetRoute(
    navController: NavController
){
    composable <MyAssetRoute> {
        MyAssetsScreen(
            navigateToAssetDetails = {
                navController.toAssetDetailsScreen(it)
            },
            navigateToAddAsset = {
                navController.toAddAssetScreen()
            }
        )
    }
}

fun NavController.toMyAsset(){
    navigate(MyAssetRoute)
}