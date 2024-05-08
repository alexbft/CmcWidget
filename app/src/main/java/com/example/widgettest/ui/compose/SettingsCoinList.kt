package com.example.widgettest.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.widgettest.data.CoinNameAndSymbol
import com.example.widgettest.data.CoinNameRepo
import com.example.widgettest.data.CoinNameRepoImpl
import com.example.widgettest.ui.theme.WidgetTestTheme

@Composable
fun SettingsCoinList(
    coinNameRepo: CoinNameRepo,
    selectedCoins: List<CoinNameAndSymbol>,
    onNavigateBack: () -> Unit = {},
    onAddCoin: (coin: CoinNameAndSymbol) -> Unit = {},
    onRemoveCoin: (coin: CoinNameAndSymbol) -> Unit = {},
    onSwapCoin: (fromIndex: Int, toIndex: Int) -> Unit = { _, _ -> },
) {
    Scaffold(topBar = {
        SettingsSimpleAppBar(title = {
            Text("Add or remove coins")
        }, onBack = onNavigateBack)
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CoinPicker(coinNameRepo = coinNameRepo, onCoinPicked = onAddCoin)
            CoinChipList(
                coins = selectedCoins,
                onRemoveCoin = onRemoveCoin,
                onSwapCoin = onSwapCoin,
            )
        }
    }
}

@Preview
@Composable
fun SettingsCoinListPreview() {
    val allCoins = listOf(
        CoinNameAndSymbol("BTC", "Bitcoin"),
        CoinNameAndSymbol("ETH", "Ethereum"),
        CoinNameAndSymbol("USDT", "USD Tether"),
        CoinNameAndSymbol("LTC", "Litecoin"),
    )
    val previewRepo = CoinNameRepoImpl(allCoins)
    WidgetTestTheme {
        SettingsCoinList(previewRepo, allCoins.take(3))
    }
}