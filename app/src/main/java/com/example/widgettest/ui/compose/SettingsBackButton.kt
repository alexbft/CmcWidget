package com.example.widgettest.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun SettingsBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
    }
}