package com.ramadan.homecare.ui.features.settings


data class SettingsUiState(
    val theme: Theme = Theme.System,
    val language: Language = Language.English,
    val showBottomSheet: Boolean = false

)

enum class Theme{
    System,
    Light,
    Dark
}
enum class Language {
    English,
    Arabic,
}
