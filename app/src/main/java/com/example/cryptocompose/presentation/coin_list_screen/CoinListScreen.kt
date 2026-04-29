package com.example.cryptocompose.presentation.coin_list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cryptocompose.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {

    val coinstate by viewModel.state.collectAsStateWithLifecycle()
    var selectedChip by rememberSaveable { mutableIntStateOf(0) }

    var listOfCoins = when (selectedChip) {
        1 -> coinstate.topGainers
        2 -> coinstate.topLosers
        else -> coinstate.list
    }
//
//    if (selectedChip == 1){
//        listOfCoins = coinstate.topGainers
//    } else if (selectedChip == 2){
//        listOfCoins = coinstate.topLosers
//    } else{
//        listOfCoins = coinstate.list
//    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ArepaX Crypto",
                        fontSize = 24.sp
                    )
                }
            )
        }
    ) { paddingValues ->

        PullToRefreshBox(
            isRefreshing = coinstate.isRefreshing,
            onRefresh = viewModel::refreshCoins,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {
                if (coinstate.errorMessage != null) {
                    Text(
                        text = coinstate.errorMessage.toString(),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                LazyRow(modifier = Modifier.padding(8.dp)) {
                    items(3) { i ->
                        AssistChip(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            onClick = { selectedChip = i },
                            label = {
                                Text(
                                    text = when(i){
                                        0 -> "Most Popular"
                                        1 -> "Top Gainers"
                                        else -> "Losers"
                                    }
                                )
                            },
                            colors = AssistChipDefaults.elevatedAssistChipColors(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = listOfCoins,
                        key = {coin -> coin.id}
                    ){ coin ->
                        CoinListItem(
                            coin = coin,
                            onItemClick = {
                                navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
                            }
                        )

                        HorizontalDivider(
                            color = Color.Gray.copy(alpha = 0.2f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}
