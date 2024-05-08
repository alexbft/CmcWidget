package com.example.widgettest.ui.compose.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.widgettest.MyApp
import com.example.widgettest.data.CoinNameAndSymbol
import com.example.widgettest.data.SelectedCoinsRepo
import kotlinx.coroutines.launch

class CoinListViewModel(private val selectedCoinsRepo: SelectedCoinsRepo) : ViewModel() {
    val selectedCoins = selectedCoinsRepo.selectedCoinsFlow

    init {
        viewModelScope.launch {
            selectedCoinsRepo.loadSelectedCoins()
        }
    }

    fun addCoin(coin: CoinNameAndSymbol) {
        viewModelScope.launch {
            selectedCoinsRepo.updateSelectedCoins { coins ->
                if (coin !in coins) coins + coin else coins
            }
        }
    }

    fun removeCoin(coin: CoinNameAndSymbol) {
        viewModelScope.launch {
            selectedCoinsRepo.updateSelectedCoins { coins ->
                coins.filter { it != coin }
            }
        }
    }

    fun swapCoin(fromIndex: Int, toIndex: Int) {
        viewModelScope.launch {
            selectedCoinsRepo.updateSelectedCoins { coins ->
                val mutCoins = coins.toMutableList()
                val coin = mutCoins.removeAt(fromIndex)
                mutCoins.add(toIndex, coin)
                mutCoins
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as MyApp
                CoinListViewModel(app.selectedCoinsRepo)
            }
        }
    }
}