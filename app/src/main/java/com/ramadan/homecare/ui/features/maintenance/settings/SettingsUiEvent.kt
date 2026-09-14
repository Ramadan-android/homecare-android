package com.ramadan.homecare.ui.features.maintenance.settings

sealed interface SettingsUiEvent {
    data class ThemeChanged(val theme: Theme) : SettingsUiEvent
    data class LanguageChanged(val language: Language) : SettingsUiEvent
    data object ToggleShowBottomSheet : SettingsUiEvent


}

