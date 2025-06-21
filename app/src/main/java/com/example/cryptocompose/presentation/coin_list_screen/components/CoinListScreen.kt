package com.example.cryptocompose.presentation.coin_list_screen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cryptocompose.presentation.navigation.Screen
import com.example.cryptocompose.presentation.coin_list_screen.CoinListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {

    val coinstate by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ArepaX Crypto",
                        fontSize = 24.sp
                    )
                },
            )
        },
        content = { paddingValues ->

            Column(modifier = Modifier.padding(paddingValues)) {

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    items(coinstate.size){ coin ->

                        CoinListItem(coin = coinstate.get(coin), onItemClick = {
                            navController.navigate(Screen.CoinDetailScreen.route + "/${it.id}")
                        })
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.5.dp, )
                    }
                }

            }

        }
    )

}