package com.ramadan.homecare.route

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ramadan.homecare.ui.features.assets.addasset.addAssetRoute
import com.ramadan.homecare.ui.features.assets.assetdetails.assetDetailsRoute
import com.ramadan.homecare.ui.features.assets.myassets.myAssetRoute

@Composable
fun HomeCareNavGraph(
    navController: NavHostController,
    startDestination: Any,
    innerPadding: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        addAssetRoute(navController = navController)
        assetDetailsRoute(navController = navController)
        myAssetRoute(navController = navController)
    }
}