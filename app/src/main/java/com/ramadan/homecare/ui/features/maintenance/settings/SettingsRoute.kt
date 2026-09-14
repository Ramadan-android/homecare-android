package com.ramadan.homecare.ui.features.maintenance.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramadan.homecare.route.SettingsRoute

fun NavGraphBuilder.settingsRoute(navController: NavController){
    composable <SettingsRoute>{
        SettingsRouteScreen(
            navigateBack = navController::popBackStack
        )

    }
}

fun NavController.toSettings(){
    navigate(SettingsRoute)
}