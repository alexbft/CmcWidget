package com.example.widgettest

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.widgettest.api.CmcProxyService
import com.example.widgettest.data.CoinNameAndSymbol
import com.example.widgettest.data.CoinNameRepo
import com.example.widgettest.data.CoinNameRepoImpl
import com.example.widgettest.data.CoinRepo
import com.example.widgettest.data.SelectedCoinsRepo
import com.example.widgettest.network.trustAllCertsOkHttpClient
import com.example.widgettest.ui.model.CoinViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MyApp : Application() {
    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    val coinNameRepo: CoinNameRepo by lazy {
        val lines = resources.openRawResource(R.raw.crypto).reader().readLines()
        val allCoins = lines.map { line ->
            val (symbol, name) = line.split(",")
            CoinNameAndSymbol(symbol, name)
        }
        CoinNameRepoImpl(allCoins)
    }

    val selectedCoinsRepo by lazy {
        SelectedCoinsRepo(dataStore)
    }

    val coinModel by lazy {
        val settings = MyAppSettings(this)
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://${settings.cmcProxyHost}:${settings.cmcProxyPort}")
            //.baseUrl("http://10.0.2.2:${settings.cmcProxyPort}")
            .client(trustAllCertsOkHttpClient())
            .build()
        val cmcProxyService = retrofit.create(CmcProxyService::class.java)
        CoinViewModel(
            CoinRepo(cmcProxyService, settings.cmcProxyClientKey),
            selectedCoinsRepo,
        )
    }
}

val Context.myApp get() = applicationContext as MyApp
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")