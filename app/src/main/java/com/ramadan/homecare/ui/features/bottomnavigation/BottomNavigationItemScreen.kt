package com.ramadan.homecare.ui.features.bottomnavigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun BottomNavigationScreen(
    viewModel: BottomNavigationViewModel = hiltViewModel()
){
    Scaffold(
        bottomBar = {  }
    ) {

    }
}

