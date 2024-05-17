package com.example.widgettest.data

import kotlinx.serialization.Serializable

@Serializable
data class CmcProxyServiceResponse(
    val statusCode: Int,
    val message: String?,
    val data: QuotesResponse?
)

@Serializable
data class QuotesResponse(
    val quotes: List<Quote>
)

@Serializable
data class Quote(
    val status: String,
    val ticker: String,
    val price: Double?,
    val change1h: Double?,
    val change24h: Double?,
    val change7d: Double?,
)
