package com.ramadan.homecare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramadan.homecare.route.HomeCareNavGraph
import com.ramadan.homecare.route.HomeDashboardRoute
import com.ramadan.homecare.route.MyAssetRoute
import com.ramadan.homecare.route.SettingsRoute
import com.ramadan.homecare.ui.theme.HomeCareTheme
import com.ramadan.homecare.ui.uicomponents.bottomnavigation.HomeCareBottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeCareTheme {
                HomeCareApp()
            }
        }
    }
}


@Composable
fun HomeCareApp() {
    val navController = rememberNavController()

    val navBackStackEntry =
        navController.currentBackStackEntryAsState().value

    val currentRoute =
        navBackStackEntry?.destination?.route

    val showBottomBar =
        currentRoute == HomeDashboardRoute::class.qualifiedName ||
                currentRoute == MyAssetRoute::class.qualifiedName ||
                currentRoute == SettingsRoute::class.qualifiedName

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar)
                HomeCareBottomBar(
                    navController = navController
                )
        }
    ) { innerPadding ->

        HomeCareNavGraph(
            navController = navController,
            startDestination = HomeDashboardRoute,
            innerPadding = innerPadding
        )
    }
}