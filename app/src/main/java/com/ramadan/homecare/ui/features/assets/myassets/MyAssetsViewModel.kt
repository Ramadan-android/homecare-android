package com.ramadan.homecare.ui.features.assets.myassets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramadan.homecare.domain.usecase.GetAssetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAssetsViewModel @Inject constructor(
    private val getAssetsUseCase: GetAssetsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MyAssetsUiState())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<MyAssetsUiEvent>()
    val event = _event.asSharedFlow()

    init {
        observeMyAssets()
    }

    private fun observeMyAssets(){
        viewModelScope.launch {
            getAssetsUseCase.getAssets().collect {listAsset->
                _state.update {
                    it.copy(
                        myAssets = listAsset
                    )
                }
            }
        }
    }

}