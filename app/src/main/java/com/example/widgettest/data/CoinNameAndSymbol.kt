package com.example.widgettest.data

data class CoinNameAndSymbol(val symbol: String, val name: String) {
    fun display() = if (symbol == name) symbol else "$symbol — $name"
}
