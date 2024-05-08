package com.example.widgettest.data

import com.example.widgettest.api.CmcProxyService

class CoinRepo(
    private val cmcProxyService: CmcProxyService,
    private val clientKey: String) {
    suspend fun getCoinListData(tickers: List<String>): CoinListData {
        if (tickers.isEmpty()) {
            return CoinListData(emptyList())
        }
        val response = cmcProxyService.quotes(
            symbols = tickers.joinToString(","),
            clientKey = clientKey)
        if (response.statusCode != 200) {
            throw Exception("Unexpected status code in response: $response")
        }
        val coins = response.data!!.quotes.mapNotNull { quote ->
            if (quote.status != "ok") {
                return@mapNotNull null
            }
            CoinData(
                ticker = quote.ticker,
                price = Price(quote.price!!),
                change1h = quote.change1h!!,
                change24h = quote.change24h!!
            )
        }
        return CoinListData(coins)
    }
}