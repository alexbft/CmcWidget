package com.example.widgettest.ui.model

import com.example.widgettest.data.CoinListData

data class CoinUiState(val loadingState: LoadingState, val coinListData: CoinListData?)

sealed interface LoadingState {
    data object Loading : LoadingState

    data class Failure(val reason: String) : LoadingState

    data object Success : LoadingState
}


