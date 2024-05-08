package com.example.widgettest.data

import com.example.widgettest.data.trie.Trie

class CoinNameRepoImpl(private val allCoins: List<CoinNameAndSymbol>) : CoinNameRepo {
    private val mapBySymbol = allCoins.associateBy { it.symbol }
    private val trieBySymbol = Trie.fromMap(mapBySymbol)
    private val trieByName = Trie.fromMap(allCoins.associateBy { it.name })

    override fun getMatches(searchString: String, limit: Int): List<CoinNameAndSymbol> {
        if (searchString.isEmpty()) {
            return allCoins.take(limit)
        }
        val result = mutableListOf<CoinNameAndSymbol>()
        val symbolStart = trieBySymbol.lookupPrefix(searchString, limit)
        result.addAll(symbolStart.map { it.second })
        if (result.size >= limit) {
            return result
        }
        val nameStart = trieByName.lookupPrefix(searchString, limit)
        val nameStartFiltered =
            nameStart.map { it.second }.filter { it !in result }.take(limit - result.size)
        result.addAll(nameStartFiltered)
        return result
    }

    override fun getExact(symbol: String): CoinNameAndSymbol? {
        return mapBySymbol[symbol]
    }
}