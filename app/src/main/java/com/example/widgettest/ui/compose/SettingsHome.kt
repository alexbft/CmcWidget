package com.example.widgettest.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.widgettest.ui.theme.WidgetTestTheme

@Composable
fun SettingsHome(
    onNavigateBack: () -> Unit = {},
    onNavigateCoinList: () -> Unit = {},
    onNavigateAbout: () -> Unit = {}) {
    Scaffold(topBar = {
        SettingsSimpleAppBar(title = {
            Text(text = "CoinList settings")
        }, onBack = onNavigateBack)
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            SettingsMenu {
                SettingsMenuItem(onClick = onNavigateCoinList) {
                    Text(text = "Add or remove coins")
                }
                SettingsMenuItem(onClick = onNavigateAbout) {
                    Text(text = "About CoinList")
                }
            }
        }
    }
}

@Composable
@Preview
fun SettingsHomePreview() {
    WidgetTestTheme {
        SettingsHome()
    }
}