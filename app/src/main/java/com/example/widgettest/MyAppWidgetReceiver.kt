package com.example.widgettest

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.example.widgettest.ui.MyAppWidget
import com.example.widgettest.ui.model.CoinViewModel

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyAppWidget()
}