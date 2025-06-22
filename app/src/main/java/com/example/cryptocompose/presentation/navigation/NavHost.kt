package com.example.cryptocompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptocompose.presentation.coin_list_screen.components.CoinListScreen
import com.example.cryptocompose.presentation.detail_coin_screen.components.CoinDetailScreen

@Composable
fun NavigationComponent() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,

        startDestination = Screen.CoinListScreen.route){
        composable(route = Screen.CoinListScreen.route){
            CoinListScreen(navController)
        }

        composable(
            route = Screen.CoinDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type = NavType.StringType }))
        {
                id -> id.arguments?.getString("id")?.let { it -> CoinDetailScreen(id = it) }
        }
    }
}