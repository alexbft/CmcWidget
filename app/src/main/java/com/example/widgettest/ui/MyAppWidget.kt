package com.example.widgettest.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.glance.GlanceId
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.example.widgettest.myApp
import com.example.widgettest.ui.glance.WidgetContent
import com.example.widgettest.ui.model.CoinViewModel
import kotlinx.coroutines.launch

class MyAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val coinViewModel = context.myApp.coinModel
        coinViewModel.loadPreferencesAndRefresh()
//        val workManager = WorkManager.getInstance(context)
//        val workRequest = PeriodicWorkRequestBuilder<RefreshWorker>(
//            repeatInterval = Duration.ofMinutes(15),
//        )
//            .addTag("REFRESH_COIN_LIST")
//            .setInitialDelay(Duration.ofSeconds(30))
//            .build()
//        workManager.enqueueUniquePeriodicWork(
//            "RefreshCoinListWork",
//            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
//            workRequest
//        )

        provideContent {
            WidgetRoot(id, coinViewModel)
        }
    }

    @Composable
    private fun WidgetRoot(glanceId: GlanceId, coinViewModel: CoinViewModel) {
        val scope = rememberCoroutineScope()
        val uiState by coinViewModel.uiState.collectAsState()
        val context = LocalContext.current
        val refreshWidget: () -> Unit = {
            scope.launch {
                coinViewModel.refreshData()
                MyAppWidget().update(context, glanceId)
            }
        }

        WidgetContent(uiState = uiState, refreshAction = refreshWidget)
    }
}

//class RefreshWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
//    override suspend fun doWork(): Result {
//        val coinViewModel = applicationContext.myApp.coinModel
//        coinViewModel.refreshData()
//        MyAppWidget().updateAll(applicationContext)
//        return Result.success()
//    }
//}