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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    var selectedChip by rememberSaveable { mutableIntStateOf(0) }

    var listOfCoins = coinstate.list

    if (selectedChip == 1){
        listOfCoins = coinstate.topGainers
    } else if (selectedChip == 2){
        listOfCoins = coinstate.topLosers
    } else{
        listOfCoins = coinstate.list
    }

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

                LazyRow(modifier = Modifier.padding(8.dp)) {
                    items(3){ i->
                        AssistChip(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            onClick = { selectedChip = i },
                            label = {
                                if (i == 0){
                                    Text("Most Popular")
                                }
                                if (i == 1){
                                    Text("Gainers")
                                }
                                if (i == 2){
                                    Text("Losers")
                                }
                            },
                            //colors = AssistChipDefaults.assistChipColors(),
                            colors = AssistChipDefaults.elevatedAssistChipColors(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ChipElevation(
                                elevation = 0.dp,
                                pressedElevation = 0.dp,
                                hoveredElevation = 0.dp,
                                draggedElevation = 0.dp,
                                disabledElevation = 0.dp,
                                focusedElevation = 2.dp)
                        )

                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(listOfCoins.size){ coin ->

                        CoinListItem(coin = listOfCoins.get(coin), onItemClick = {
                            navController.navigate(Screen.CoinDetailScreen.route + "/${it.id}")
                        })
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.5.dp, )
                    }
                }

            }
        }
    )
}

@Composable
fun CustomChip (i: Int){



}