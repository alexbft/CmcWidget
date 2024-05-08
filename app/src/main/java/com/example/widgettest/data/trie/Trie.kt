package com.example.widgettest.data.trie

class Trie<V> {
    private val root = Node<V>()

    fun insert(key: String, value: V) {
        root.insert(key.lowercase() to value, 0)
    }

    fun lookupPrefix(prefix: String, limit: Int): List<Pair<String, V>> {
        return root.lookupPrefix(prefix.lowercase(), 0, limit)
    }

    companion object {
        fun <V> fromMap(map: Map<String, V>): Trie<V> {
            val result = Trie<V>()
            for (entry in map) {
                result.insert(entry.key, entry.value)
            }
            return result
        }
    }
}