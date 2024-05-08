package com.example.widgettest.data

interface CoinNameRepo {
    fun getMatches(searchString: String, limit: Int = 5): List<CoinNameAndSymbol>
    fun getExact(symbol: String): CoinNameAndSymbol?
}