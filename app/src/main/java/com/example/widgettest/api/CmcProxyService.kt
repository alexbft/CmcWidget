package com.example.widgettest.api

import com.example.widgettest.data.CmcProxyServiceResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CmcProxyService {
    @GET("quotes")
    suspend fun quotes(
        @Query("symbol") symbols: String,
        @Header("X-CLIENT-KEY") clientKey: String): CmcProxyServiceResponse
}