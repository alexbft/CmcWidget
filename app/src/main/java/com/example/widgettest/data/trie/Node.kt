package com.example.widgettest.data.trie

internal class Node<V> {
    private val children = mutableMapOf<Char, Node<V>>()
    private var exactMatchData: Pair<String, V>? = null
    private var leafData: Pair<String, V>? = null

    private val isLeaf: Boolean
        get() = exactMatchData == null && children.isEmpty()

    private fun all(limit: Int): List<Pair<String, V>> {
        if (isLeaf) {
            return leafData?.let { listOf(it) } ?: emptyList()
        }
        val result = mutableListOf<Pair<String, V>>()
        exactMatchData?.let { result.add(it) }
        for (childNode in children.values) {
            if (result.size >= limit) {
                break
            }
            result.addAll(childNode.all(limit - result.size))
        }
        return result
    }

    fun lookupPrefix(prefix: String, index: Int, limit: Int): List<Pair<String, V>> {
        if (index >= prefix.length) {
            return all(limit)
        }
        if (isLeaf) {
            return leafData?.let {
                if (it.first.startsWith(prefix)) listOf(it) else emptyList()
            } ?: emptyList()
        }
        val childNode = children[prefix[index]]
        return childNode?.lookupPrefix(prefix, index + 1, limit) ?: emptyList()
    }

    fun insert(data: Pair<String, V>, index: Int) {
        if (isLeaf && leafData == null) {
            leafData = data
        } else {
            val aData = leafData
            if (aData != null) {
                leafData = null
                insertNonLeaf(aData, index)
            }
            insertNonLeaf(data, index)
        }
    }

    private fun insertNonLeaf(data: Pair<String, V>, index: Int) {
        val key = data.first
        if (index >= key.length) {
            exactMatchData = data
        } else {
            val childNode = children.getOrPut(key[index]) { Node() }
            childNode.insert(data, index + 1)
        }
    }
}