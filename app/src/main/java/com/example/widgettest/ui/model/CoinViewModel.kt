package com.example.widgettest.ui.model

import android.util.Log
import com.example.widgettest.data.CoinRepo
import com.example.widgettest.data.SelectedCoinsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class CoinViewModel(
    private val repo: CoinRepo,
    private val selectedCoinsRepo: SelectedCoinsRepo,
) {
    private val _uiState = MutableStateFlow(CoinUiState(LoadingState.Loading, null))
    val uiState: StateFlow<CoinUiState> = _uiState.asStateFlow()

    suspend fun loadPreferencesAndRefresh() {
        selectedCoinsRepo.loadSelectedCoins()
        refreshData()
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            _uiState.update { state ->
                state.copy(loadingState = LoadingState.Loading)
            }
            val selectedCoinSymbols =
                selectedCoinsRepo.selectedCoinsFlow.value.map { coin -> coin.symbol }
            try {
                _uiState.value = CoinUiState(
                    loadingState = LoadingState.Success,
                    coinListData = repo.getCoinListData(selectedCoinSymbols)
                )
            } catch (e: Exception) {
                Log.e("CmcProxyService", "Exception", e)
                _uiState.update { state ->
                    state.copy(loadingState = LoadingState.Failure(e.message ?: "Internal error"))
                }
            }
        }
    }
}