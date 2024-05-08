package com.example.widgettest.ui.glance

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

@Composable
fun WidgetContentFailure(reason: String, onTryAgainClicked: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = reason,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = GlanceTheme.colors.onSecondaryContainer,
            )
        )
        Spacer(GlanceModifier.size(8.dp))
        Button(text = "Try again", onClick = onTryAgainClicked)
    }
}