package com.ramadan.homecare.ui.features.bottomnavigation

sealed interface BottomNavigationUiEvent {
    data class SelectedItem(val item: BottomNavigationItem): BottomNavigationUiEvent

}