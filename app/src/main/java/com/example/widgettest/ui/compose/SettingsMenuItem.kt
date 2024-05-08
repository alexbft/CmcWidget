package com.example.widgettest.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsMenuItem(onClick: () -> Unit, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(MaterialTheme.typography.titleMedium)
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick) {
        Row(modifier = Modifier.padding(16.dp)) {
            CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
        }
    }
}