package com.ramadan.homecare.ui.features.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationUiState(
    val selectedItem: BottomNavigationItem = BottomNavigationItem(
        title = "Home",
        icon = Icons.Default.Home,
        route = "home"
    ),
)

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)