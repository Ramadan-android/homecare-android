package com.ramadan.homecare.ui.features.maintenance.addmaintenance

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramadan.homecare.route.AddMaintenanceRoute

fun NavGraphBuilder.addMaintenanceRoute(navController: NavController){
    composable<AddMaintenanceRoute> {
        AddMaintenanceScreen(
            navigateBack = navController::popBackStack
        )
    }
}

fun NavController.toAddMaintenanceScreen(assetId: Long){
    navigate(AddMaintenanceRoute(assetId))
}