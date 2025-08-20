package com.example.cryptocompose.presentation.detail_coin_screen.components

import android.annotation.SuppressLint
import android.text.Layout
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
import java.text.NumberFormat
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    viewModel : CoinDetailViewModel = hiltViewModel(),
    id: String
) {
    val modelCoin = viewModel.getCoin(id)
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text( text = "${state?.symbol?.uppercase()}", style = MaterialTheme.typography.titleMedium, color = Color(0xFFA9C7C7))
                },
            )
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            item {

                val price = state?.marketData?.priceChangePercentage24h ?: 0.0
                val textColor = if (price >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                Row (
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start

                ){
                    Column(
                        modifier = Modifier.padding(2.dp),
                    ) {

                        Row (modifier = Modifier.padding(horizontal = 8.dp)) {
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
                                text = "${state?.name}",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFFA9C7C7)
                            )
                        }
                        Text(
                            text = "$${state?.marketData?.currentPrice?.usd}",
                            style = MaterialTheme.typography.titleLarge,
                            color = textColor
                        )

                        Row (modifier = Modifier.padding(horizontal = 8.dp)) {

                            val numberFormat = NumberFormat.getInstance()
                            Text(
                                text = "$${numberFormat.format(abs(price))}",
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                text = " (${state?.marketData?.priceChangePercentage24h}%)",
                                style = MaterialTheme.typography.titleSmall,
                                color = textColor
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
                Text(
                    text = "Key Stats",
                    style = MaterialTheme.typography.titleMedium
                )
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

                        )
                        Text(
                            text = "Low(24h): $${state?.marketData?.low24h?.usd}",
                            style = MaterialTheme.typography.titleSmall,

                            )

                    }
                    Row (horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(4.dp)

                    ) {
                        Text(
                            text = "Ath: $${state?.marketData?.ath?.usd}",
                            style = MaterialTheme.typography.titleSmall,

                            )

                        Text(
                            text = "Vol(24h): $${state?.marketData?.totalVolume?.usd}",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }

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
