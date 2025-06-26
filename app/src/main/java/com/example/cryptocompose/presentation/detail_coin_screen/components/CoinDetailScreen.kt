package com.example.cryptocompose.presentation.detail_coin_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.cryptocompose.presentation.detail_coin_screen.CoinDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    viewModel : CoinDetailViewModel = hiltViewModel(),
    id: String
) {
    val modelCoin = viewModel.getCoin(id)
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        AsyncImage(
                            model = state?.image?.small,
                            contentDescription = "${state?.name} logo",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${state?.name} (${state?.symbol?.uppercase()})",
                            style = MaterialTheme.typography.titleLarge
                        )


                    }
                },
            )
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            item {

                val priceChange24h = state?.marketData?.priceChangePercentage24h ?: 0.0
                val textColor = if (priceChange24h >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)

                HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.5.dp, )
                Row (
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround

                ){

                    Column(
                        modifier = Modifier.padding(6.dp),

                    ) {
                        Text(
                            text = "$${state?.marketData?.currentPrice?.usd}",
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor
                        )
                        Text(
                            text = "Rank: ${state?.marketData?.marketCapRank}",
                            style = MaterialTheme.typography.titleSmall,

                        )
                    }
                    Column(
                        modifier = Modifier.padding(6.dp),

                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "High(24h): $${state?.marketData?.high24h?.usd}",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(horizontal = 6.dp)

                            )
                            Text(
                                text = "Low(24h): $${state?.marketData?.low24h?.usd}",
                                style = MaterialTheme.typography.titleSmall,

                            )

                        }
                        Row (horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.padding(4.dp)

                        ) {
//                            Text(
//                                text = "Vol(24h): $${state?.marketData?.totalVolume?.usd}",
//                                style = MaterialTheme.typography.titleSmall,
//
//                            )
                            Text(
                                text = "Ath: $${state?.marketData?.ath?.usd}",
                                style = MaterialTheme.typography.titleSmall,

                            )
                        }
                    }

                }

                HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.5.dp, )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Chart",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Description",
                    style = MaterialTheme.typography.titleMedium
                    )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${state?.description?.en}",
                    fontSize = 16.sp,

                )

            }

        }

    }
}
