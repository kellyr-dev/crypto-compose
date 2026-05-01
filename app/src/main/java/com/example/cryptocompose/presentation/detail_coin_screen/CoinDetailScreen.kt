package com.example.cryptocompose.presentation.detail_coin_screen

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.cryptocompose.data.mappers.ChartPoint
import com.example.cryptocompose.data.mappers.ChartRange
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.presentation.coin_list_screen.PriceChangeBadge
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel(),
    id: String,
    onBackClick: () -> Unit
) {
    LaunchedEffect(id) {
        viewModel.loadCoin(id)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val coin = state.coin

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = coin?.symbol?.uppercase() ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFA9C7C7)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoadingCoin) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.errorMessage != null) {
                DetailErrorState(
                    message = state.errorMessage ?: "Unknown error",
                    onRetry = { viewModel.loadCoin(id) }
                )
            } else {
                coin?.let { nonNullCoin ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            CoinHeaderCard(coin = nonNullCoin)
                        }

                        item {
                            ChartCard(
                                points = state.chart?.points ?: emptyList(),
                                selectedRange = state.selectedRange,
                                isLoading = state.isLoadingChart,
                                onRangeSelected = { viewModel.onChartRangeSelected(id, it) }
                            )
                        }

                        item {
                            KeyStatsCard(coin = nonNullCoin)
                        }

                        item {
                            AboutCard(description = nonNullCoin.description.en.stripHtml())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CoinHeaderCard(
    coin: CoinDetail
) {
    val currentPrice = coin.marketData.currentPrice.usd ?: 0.0
    val percentage24h = coin.marketData.priceChangePercentage24h ?: 0.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = coin.image.small,
                    contentDescription = coin.name,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = coin.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = coin.symbol.uppercase(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "#${coin.marketData.marketCapRank}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "$${NumberFormat.getNumberInstance().format(currentPrice)}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            PriceChangeBadge(
                change = percentage24h
            )
        }
    }
}

@Composable
fun KeyStatsCard(
    coin: CoinDetail
) {
    val numberFormat = NumberFormat.getNumberInstance()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Key Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            StatRow("Market Cap", formatLargeCurrency(coin.marketData.marketCap.usd ?: 0.0))
            StatRow("Volume", formatLargeCurrency(coin.marketData.totalVolume.usd ?: 0.0))
            StatRow("24H Low", formatLargeCurrency(coin.marketData.low24h.usd ?: 0.0))
            StatRow("24H High", formatLargeCurrency(coin.marketData.high24h.usd ?: 0.0))
            StatRow("All-time High", formatLargeCurrency(coin.marketData.ath.usd ?: 0.0))
        }
    }
}


@Composable
fun StatRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AboutCard(
    description: String
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                maxLines = if (expanded) Int.MAX_VALUE else 6,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            TextButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Read more")
            }
        }
    }
}

@Composable
fun ChartCard(
    points: List<ChartPoint>,
    selectedRange: ChartRange,
    isLoading: Boolean,
    onRangeSelected: (ChartRange) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Chart",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                CoinLineChart(
                    points = points,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ChartRangeSelector(
                selectedRange = selectedRange,
                onRangeSelected = onRangeSelected
            )
        }
    }
}


@Composable
fun DetailErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

fun String.stripHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}

fun formatLargeCurrency(value: Double): String {
    return when {
        value >= 1_000_000_000_000 -> "$${String.format("%.2fT", value / 1_000_000_000_000)}"
        value >= 1_000_000_000 -> "$${String.format("%.2fB", value / 1_000_000_000)}"
        value >= 1_000_000 -> "$${String.format("%.2fM", value / 1_000_000)}"
        value >= 1_000 -> "$${String.format("%.2fK", value / 1_000)}"
        else -> "$${String.format("%.2f", value)}"
    }
}