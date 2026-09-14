package com.ramadan.homecare.ui.features.assets.myassets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramadan.homecare.core.util.AssetCategory
import com.ramadan.homecare.domain.usecase.asset.GetAssetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAssetsViewModel @Inject constructor(
    private val getAssetsUseCase: GetAssetsUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<MyAssetsUiEffectEvent>()
    val event = _event.asSharedFlow()
    private val _query = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow(AssetCategory.All)


    val state = combine(
        getAssetsUseCase.getAssets(),
        _query,
        _selectedCategory
    ) { assets, query, selectedCategory ->
        val filteredAssets = assets.filter { asset ->
            if (selectedCategory == AssetCategory.All) return@filter true
            asset.category == selectedCategory
        }.filter {
            query.isBlank() ||
                    it.assetName.contains(query, ignoreCase = true)
                    || it.brand.contains(query, ignoreCase = true)
                    || it.model.contains(query, ignoreCase = true)
        }
        MyAssetsUiState(
            myAssets = filteredAssets,
            query = query,
            selectedCategory = selectedCategory,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyAssetsUiState(isLoading = true)
    )

    fun onEvent(event: MyAssetsUiEvent) {
        when (event) {
            is MyAssetsUiEvent.CategoryChanged -> {
                _selectedCategory.update { event.category }
            }

            is MyAssetsUiEvent.SearchQueryChanged -> {
                _query.update { event.query }
            }
        }
    }

    fun navigateToAddAsset() {
        viewModelScope.launch {
            _event.emit(MyAssetsUiEffectEvent.NavigateToAddAsset)
        }
    }

    fun navigateToAssetDetails(assetId: Long) {
        viewModelScope.launch {
            _event.emit(MyAssetsUiEffectEvent.NavigateToAssetDetails(assetId))
        }
    }
}