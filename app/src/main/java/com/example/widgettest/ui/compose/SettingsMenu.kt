package com.example.widgettest.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsMenu(content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
}