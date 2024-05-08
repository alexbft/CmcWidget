package com.example.widgettest.ui.glance

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.example.widgettest.ui.glance.theme.MyColorScheme
import com.example.widgettest.ui.model.CoinUiState
import com.example.widgettest.ui.model.LoadingState

@Composable
fun WidgetContent(uiState: CoinUiState, refreshAction: () -> Unit) {
    GlanceTheme(colors = MyColorScheme.colors) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.widgetBackground),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.coinListData != null) {
                CoinList(coinListData = uiState.coinListData, refreshAction)
            } else {
                when (val loadingState = uiState.loadingState) {
                    LoadingState.Loading -> WidgetContentLoading()

                    is LoadingState.Failure -> WidgetContentFailure(
                        reason = loadingState.reason,
                        onTryAgainClicked = refreshAction
                    )

                    LoadingState.Success ->
                        throw IllegalArgumentException("CoinListData is null")
                }
            }
            Box(
                modifier = GlanceModifier.fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                WidgetBottomIcons(uiState.loadingState)
            }
        }
    }
}