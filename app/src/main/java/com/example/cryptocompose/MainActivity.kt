package com.example.cryptocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptocompose.presentation.coin_list_screen.components.CoinListScreen
import com.example.cryptocompose.presentation.detail_coin_screen.components.CoinDetailScreen
import com.example.cryptocompose.presentation.navigation.Screen
import com.example.cryptocompose.presentation.ui.theme.CryptoComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoComposeTheme {
                Surface (color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = Screen.CoinListScreen.route
                        ){
                            composable(
                                route = Screen.CoinListScreen.route
                            ){
                                CoinListScreen(navController)
                            }

                            composable(
                                route = Screen.CoinDetailScreen.route + "/{id}",
                                arguments = listOf( navArgument(name = "id"){
                                    type = NavType.StringType
                                })
                            ){
                                id -> id.arguments?.getString("id")?.let { it -> CoinDetailScreen(id = it) }
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoComposeTheme {
        Greeting("Android")
    }
}