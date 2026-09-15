package com.ramadan.homecare.ui.features.homedashboard

sealed interface HomeDashboardUiEffectEvent {
    data object AddAsset: HomeDashboardUiEffectEvent
}