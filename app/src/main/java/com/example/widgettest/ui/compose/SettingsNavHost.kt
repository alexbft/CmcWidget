package com.example.widgettest.ui.compose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.widgettest.myApp
import com.example.widgettest.ui.compose.model.CoinListViewModel

@Composable
fun SettingsNavHost(
    navHostController: NavHostController = rememberNavController(),
    coinListViewModel: CoinListViewModel = viewModel(factory = CoinListViewModel.Factory)
) {
    val currentActivity = LocalContext.current as Activity
    NavHost(navController = navHostController, startDestination = "home") {
        composable("home") {
            SettingsHome(onNavigateBack = {
                if (!navHostController.navigateUp()) {
                    currentActivity.finish()
                }
            }, onNavigateAbout = {
                navHostController.navigate("about")
            }, onNavigateCoinList = {
                navHostController.navigate("coin-list")
            })
        }
        composable("about") {
            SettingsAbout(onNavigateBack = {
                navHostController.popBackStack("home", inclusive = false)
            })
        }
        composable("coin-list") {
            val coinNameRepo = LocalContext.current.myApp.coinNameRepo
            val coins by coinListViewModel.selectedCoins.collectAsState()
            SettingsCoinList(
                coinNameRepo = coinNameRepo,
                selectedCoins = coins,
                onNavigateBack = {
                    navHostController.popBackStack("home", inclusive = false)
                },
                onAddCoin = {
                    coinListViewModel.addCoin(it)
                },
                onRemoveCoin = {
                    coinListViewModel.removeCoin(it)
                },
                onSwapCoin = { fromIndex, toIndex ->
                    coinListViewModel.swapCoin(fromIndex, toIndex)
                }
            )
        }
    }
}