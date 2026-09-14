package com.ramadan.homecare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ramadan.homecare.route.HomeCareNavGraph
import com.ramadan.homecare.route.MyAssetRoute
import com.ramadan.homecare.route.SettingsRoute
import com.ramadan.homecare.ui.features.assets.addasset.AddAssetScreen
import com.ramadan.homecare.ui.features.assets.assetdetails.AssetDetailsScreen
import com.ramadan.homecare.ui.features.assets.myassets.MyAssetsScreen
import com.ramadan.homecare.ui.theme.HomeCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeCareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeCareApp(innerPadding)
                }
            }
        }
    }
}


@Composable
fun HomeCareApp(innerPadding: PaddingValues){
    val navController = rememberNavController()
    HomeCareNavGraph(
        navController = navController,
        startDestination = SettingsRoute,
        innerPadding = innerPadding
    )
}