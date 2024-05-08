package com.example.widgettest.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CoinChip(
    symbol: String,
    isDragging: Boolean,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    var itemModifier = modifier
        .width(272.dp)
        .height(40.dp)
    if (isDragging) {
        itemModifier = itemModifier
            .zIndex(1f)
    }
    InputChip(
        modifier = itemModifier,
        selected = isDragging,
        onClick = { },
        label = {
            Text(text = symbol, style = MaterialTheme.typography.labelLarge)
        },
        trailingIcon = {
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Clear, "Remove")
            }
        }
    )
}