package com.example.widgettest.ui.glance

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.size
import androidx.glance.layout.width
import com.example.widgettest.MainActivity
import com.example.widgettest.R
import com.example.widgettest.ui.model.LoadingState

@Composable
fun WidgetBottomIcons(loadingState: LoadingState) {
    Row {
        if (loadingState == LoadingState.Loading) {
            CircularProgressIndicator(modifier = GlanceModifier.size(32.dp))
            Spacer(GlanceModifier.width(8.dp))
        } else if (loadingState is LoadingState.Failure) {
            Image(
                provider = ImageProvider(R.drawable.baseline_warning_amber_24),
                contentDescription = loadingState.reason,
            )
            Spacer(GlanceModifier.width(8.dp))
        }
        Image(
            modifier = GlanceModifier.clickable(
                actionStartActivity<MainActivity>()
            ),
            provider = ImageProvider(R.drawable.baseline_settings_24),
            contentDescription = "Open settings")
    }
}