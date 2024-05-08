package com.example.widgettest.ui.glance

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.clickable
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.widgettest.data.CoinListData
import com.example.widgettest.ui.glance.theme.MyColorScheme

@Composable
fun CoinList(coinListData: CoinListData, onClick: () -> Unit) {
    Box(
        modifier = GlanceModifier.fillMaxSize()
            .background(GlanceTheme.colors.background)
            .padding(16.dp)
            .clickable(onClick)
    ) {
        LazyColumn(
            modifier = GlanceModifier
                .fillMaxWidth()
                .clickable(onClick),
        ) {
            items(items = coinListData.coins) { coin ->
                val color1h = if (coin.change1h >= 0) MyColorScheme.Green else MyColorScheme.Red
                val color24h = if (coin.change24h >= 0) MyColorScheme.Green else MyColorScheme.Red
                val colorPrice = color1h
                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .clickable(onClick)
                        .height(28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = GlanceModifier.width(80.dp).height(27.dp),
                        text = coin.ticker,
                        style = TextStyle(
                            color = GlanceTheme.colors.onBackground,
                            fontSize = 20.sp,
                        )
                    )
                    Text(
                        modifier = GlanceModifier.defaultWeight(),
                        text = coin.price.formatAsCurrency(),
                        style = TextStyle(
                            color = ColorProvider(colorPrice),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Right,
                        )
                    )
                    Text(
                        modifier = GlanceModifier.width(64.dp),
                        text = String.format("%.2f%%", coin.change1h * 100),
                        style = TextStyle(
                            color = ColorProvider(color1h),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Right,
                        )
                    )
                    Text(
                        modifier = GlanceModifier.width(64.dp),
                        text = String.format("%.2f%%", coin.change24h * 100),
                        style = TextStyle(
                            color = ColorProvider(color24h),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Right,
                        )
                    )
                }
            }
        }
    }
}