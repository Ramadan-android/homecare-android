package com.ramadan.homecare.ui.features.homedashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramadan.homecare.route.HomeDashboardRoute
import com.ramadan.homecare.ui.features.assets.addasset.toAddAssetScreen

fun NavGraphBuilder.homeDashboardRoute(navController: NavController){
    composable <HomeDashboardRoute>{
        HomeDashboardScreen(
            navigateToAddAsset = {
                navController.toAddAssetScreen()
            }
        )
    }
}

fun NavController.toHomeDashboard(){
    navigate(HomeDashboardRoute)
}