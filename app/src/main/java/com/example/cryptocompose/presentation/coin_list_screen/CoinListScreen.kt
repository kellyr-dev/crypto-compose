package com.example.cryptocompose.presentation.coin_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {

    val coinstate by viewModel.state.collectAsStateWithLifecycle()
    var selectedChip by rememberSaveable { mutableIntStateOf(0) }

    val listOfCoins = when (selectedChip) {
        1 -> coinstate.topGainers
        2 -> coinstate.topLosers
        else -> coinstate.list
    }

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
                                    text = when (i) {
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


                OutlinedTextField(
                    value = coinstate.searchQuery,
                    onValueChange = viewModel::onSearchQueryChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    placeholder = {
                        Text("Search coins...")
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        if (coinstate.searchQuery.isNotBlank()) {
                            IconButton(
                                onClick = { viewModel.onSearchQueryChanged("") }
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                if (listOfCoins.isEmpty() && coinstate.searchQuery.isNotBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No coins found")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = listOfCoins,
                            key = { coin -> coin.id }
                        ) { coin ->
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
}

