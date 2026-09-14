package com.ramadan.homecare.ui.features.maintenance.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramadan.homecare.core.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUiState())
    val state = _state.asStateFlow()


    init {
        val savedTheme = dataStore.data.map {
            it[Constants.THEME_ID]
        }.flowOn(Dispatchers.IO)
        val savedLanguage = dataStore.data.map {
            it[Constants.LANGUAGE_ID]
        }.flowOn(Dispatchers.IO)
        viewModelScope.launch {
            savedTheme.first()?.let { theme ->
                _state.update {
                    it.copy(
                        theme = Theme.valueOf(theme)
                    )
                }
            }
            savedLanguage.first()?.let { language ->
                _state.update {
                    it.copy(
                        language = Language.valueOf(language)
                    )
                }
            }
        }
    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.LanguageChanged -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            language = event.language
                        )
                    }
                    dataStore.edit { preferences ->
                        preferences[Constants.LANGUAGE_ID] = event.language.name
                    }
                }
            }

            is SettingsUiEvent.ThemeChanged -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            theme = event.theme
                        )
                    }
                    dataStore.edit { preferences ->
                        preferences[Constants.THEME_ID] = event.theme.name
                    }
                }
            }

            SettingsUiEvent.ToggleShowBottomSheet -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            showDeleteDialog = !_state.value.showDeleteDialog
                        )
                    }
                }
            }
        }
    }


}