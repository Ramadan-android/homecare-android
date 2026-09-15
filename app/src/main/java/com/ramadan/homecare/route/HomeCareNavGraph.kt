package com.ramadan.homecare.route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ramadan.homecare.ui.features.assets.addasset.addAssetRoute
import com.ramadan.homecare.ui.features.assets.assetdetails.assetDetailsRoute
import com.ramadan.homecare.ui.features.assets.myassets.myAssetRoute
import com.ramadan.homecare.ui.features.homedashboard.homeDashboardRoute
import com.ramadan.homecare.ui.features.maintenance.addmaintenance.addMaintenanceRoute
import com.ramadan.homecare.ui.features.settings.settingsRoute

@Composable
fun HomeCareNavGraph(
    navController: NavHostController,
    startDestination: Any,
    innerPadding: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ){
        homeDashboardRoute(navController = navController)
        myAssetRoute(navController = navController)
        assetDetailsRoute(navController = navController)
        addAssetRoute(navController = navController)
        addMaintenanceRoute(navController = navController)
        settingsRoute(navController = navController)
    }
}