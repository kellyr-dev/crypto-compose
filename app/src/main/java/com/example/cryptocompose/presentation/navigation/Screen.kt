package com.example.cryptocompose.presentation.navigation

sealed class Screen(val route: String) {

    object CoinListScreen: Screen("coin_list_screen")
    object CoinDetailScreen: Screen("coin_detail_screen")


}