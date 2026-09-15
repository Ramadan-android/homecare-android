package com.ramadan.homecare.ui.uicomponents.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramadan.homecare.route.HomeDashboardRoute
import com.ramadan.homecare.route.MyAssetRoute
import com.ramadan.homecare.route.SettingsRoute

sealed class BottomNavItem(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = HomeDashboardRoute,
        label = "Home",
        icon = Icons.Default.Home
    )

    data object MyAssets : BottomNavItem(
        route = MyAssetRoute,
        label = "My Assets",
        icon = Icons.Default.Inventory2
    )

    data object Settings : BottomNavItem(
        route = SettingsRoute,
        label = "Settings",
        icon = Icons.Default.Settings
    )
}