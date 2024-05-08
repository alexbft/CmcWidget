package com.example.widgettest.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsAbout(
    onNavigateBack: () -> Unit = {}) {
    Scaffold(topBar = {
        SettingsSimpleAppBar(title = {
            Text(text = "About CoinList")
        }, onBack = onNavigateBack)
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Text(text = "CoinList v.1.0.0", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
@Preview
fun SettingsAboutPreview() {
    SettingsAbout()
}