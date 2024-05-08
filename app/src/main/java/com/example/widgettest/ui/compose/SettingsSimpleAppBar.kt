package com.example.widgettest.ui.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSimpleAppBar(title: @Composable ()->Unit, onBack: ()->Unit) {
    TopAppBar(title = title, navigationIcon = {
        SettingsBackButton(onClick = onBack)
    })
}