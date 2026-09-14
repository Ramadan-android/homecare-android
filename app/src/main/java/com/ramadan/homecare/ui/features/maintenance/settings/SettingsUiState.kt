package com.ramadan.homecare.ui.features.maintenance.settings


data class SettingsUiState(
    val theme: Theme = Theme.System,
    val language: Language = Language.English,
    val showDeleteDialog: Boolean = false

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
