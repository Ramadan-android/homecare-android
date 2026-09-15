package com.ramadan.homecare.ui.uicomponents.bottomnavigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hasRoute
import com.ramadan.homecare.route.HomeDashboardRoute
import com.ramadan.homecare.route.MyAssetRoute
import com.ramadan.homecare.route.SettingsRoute

@Composable
fun HomeCareBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry =
        navController.currentBackStackEntryAsState().value

    val currentDestination =
        navBackStackEntry?.destination

    NavigationBar {

        NavigationBarItem(
            selected = currentDestination?.hasRoute<HomeDashboardRoute>() == true,
            onClick = {
                navController.navigate(HomeDashboardRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = BottomNavItem.Home.icon,
                    contentDescription = BottomNavItem.Home.label
                )
            },
            label = {
                Text(BottomNavItem.Home.label)
            }
        )

        NavigationBarItem(
            selected = currentDestination?.hasRoute<MyAssetRoute>() == true,
            onClick = {
                navController.navigate(MyAssetRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = BottomNavItem.MyAssets.icon,
                    contentDescription = BottomNavItem.MyAssets.label
                )
            },
            label = {
                Text(BottomNavItem.MyAssets.label)
            }
        )

        NavigationBarItem(
            selected = currentDestination?.hasRoute<SettingsRoute>() == true,
            onClick = {
                navController.navigate(SettingsRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = BottomNavItem.Settings.icon,
                    contentDescription = BottomNavItem.Settings.label
                )
            },
            label = {
                Text(BottomNavItem.Settings.label)
            }
        )
    }
}