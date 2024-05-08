package com.example.widgettest.ui.glance

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.glance.GlanceTheme
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

@Composable
fun WidgetContentLoading() {
    Text(
        text = "Loading...",
        style = TextStyle(
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            color = GlanceTheme.colors.onSecondaryContainer,
        )
    )
}