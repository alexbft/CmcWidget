package com.example.widgettest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

private val selectedCoinsKey = stringPreferencesKey("selectedCoins")

class SelectedCoinsRepo(private val dataStore: DataStore<Preferences>) {
    private val _selectedCoinsFlow = MutableStateFlow(emptyList<CoinNameAndSymbol>())
    val selectedCoinsFlow = _selectedCoinsFlow.asStateFlow()

    private var isLoadComplete = false

    private val selectedCoinsPreferencesFlow: Flow<List<CoinNameAndSymbol>> =
        dataStore.data.map { preferences ->
            val selectedCoinsStr = preferences[selectedCoinsKey] ?: ""
            if (selectedCoinsStr.isEmpty()) {
                emptyList()
            } else {
                selectedCoinsStr.split(";")
                    .map { coinPart ->
                        val (symbol, name) = coinPart.split(",")
                        CoinNameAndSymbol(symbol, name)
                    }
            }
        }

    suspend fun loadSelectedCoins() {
        _selectedCoinsFlow.value = selectedCoinsPreferencesFlow.first()
        isLoadComplete = true
    }

    suspend fun updateSelectedCoins(updateFn: (List<CoinNameAndSymbol>) -> List<CoinNameAndSymbol>) {
        if (!isLoadComplete) {
            loadSelectedCoins()
        }
        _selectedCoinsFlow.update(updateFn)
        val selectedCoinsStr = selectedCoinsFlow.value.joinToString(";") { coin ->
            "${coin.symbol},${coin.name}"
        }
        dataStore.edit { preferences ->
            preferences[selectedCoinsKey] = selectedCoinsStr
        }
    }
}