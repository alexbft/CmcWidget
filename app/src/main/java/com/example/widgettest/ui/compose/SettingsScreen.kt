package com.example.widgettest.ui.compose

import androidx.compose.runtime.Composable
import com.example.widgettest.ui.theme.WidgetTestTheme

@Composable
fun SettingsScreen() {
    WidgetTestTheme {
        SettingsNavHost()
    }
}