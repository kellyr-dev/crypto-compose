package com.example.cryptocompose.presentation.coin_list_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cryptocompose.presentation.navigation.Screen
import com.example.cryptocompose.presentation.coin_list_screen.CoinListViewModel

@Composable
fun CoinListScreen(

    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()

) {

    val coinstate by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier =
            Modifier.fillMaxSize()
                .padding(30.dp),
        Alignment.TopCenter
        ){
        Text(text = "ArepaX Crypto",
            style = MaterialTheme.typography.titleLarge)
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(coinstate.size){ coin ->

                CoinListItem(coin = coinstate.get(coin), onItemClick = {
                    navController.navigate(Screen.CoinDetailScreen.route + "/${it.id}")
                } )
            }
        }
    }




}