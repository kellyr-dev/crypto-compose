package com.example.cryptocompose.presentation.detail_coin_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
            .padding(8.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text( text = "${state?.symbol?.uppercase()}", style = MaterialTheme.typography.titleMedium, color = Color(0xFFA9C7C7))
                }
            )
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            item {

                val price24h = state?.marketData?.priceChange24h ?: 0.0
                val currentPrice = state?.marketData?.currentPrice?.usd ?: 0.0
                val percentage24h = state?.marketData?.priceChangePercentage24h
                val numberFormat = NumberFormat.getInstance()
                val textColor = if (price24h >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
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
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFFA9C7C7)
                            )
                        }
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "$${numberFormat.format(currentPrice)}",
                            style = MaterialTheme.typography.titleLarge,
                            color = textColor
                        )

                        Row (modifier = Modifier.padding(horizontal = 12.dp)) {

                            val formatterPercentage = String.format("%.2f", percentage24h)
                            Text(
                                text = "$${numberFormat.format(price24h)}",
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(

                                text = " (${formatterPercentage}%)",
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
                    text = "Key stats",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column () {

                    val marketCap = state?.marketData?.marketCap?.usd ?: 0.0
                    val ath = state?.marketData?.ath?.usd ?: 0.0
                    val volume = state?.marketData?.totalVolume?.usd ?: 1.0
                    val low = state?.marketData?.low24h?.usd ?: 0.0
                    val high = state?.marketData?.high24h?.usd ?: 0.0

                    val totalSupply = state?.marketData?.totalSupply ?: 0.0
                    val circulSupply = state?.marketData?.circulatingSupply ?: 1.0
                    val supply = (totalSupply/circulSupply) * 100


                    // Market Rank
                    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp), Arrangement.SpaceBetween){
                        Column(modifier = Modifier.padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.Start)
                        {
                            Text( text = "Maket rank")
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text( text = "#${state?.marketData?.marketCapRank}")
                        }
                    }

                    // Market Cap
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.8.dp, )
                    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp), Arrangement.SpaceBetween){
                        Column(modifier = Modifier.padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.Start)
                        {
                            Text( text = "Maket Cap")
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text( text = "$${numberFormat.format(marketCap)}")
                        }
                    }

                    // Circulating Supply
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.8.dp, )
                    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp), Arrangement.SpaceBetween){
                        Column(modifier = Modifier.padding(horizontal = 10.dp,),
                            horizontalAlignment = Alignment.Start)
                        {
                            Text( text = "Circulating Supply")
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text( text = "${String.format("%.2f", supply)}%")
                        }
                    }


                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.8.dp, )
                    // 24H volume
                    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp), Arrangement.SpaceBetween){
                        Column(modifier = Modifier.padding(horizontal = 10.dp,),
                            horizontalAlignment = Alignment.Start)
                        {
                            Text( text = "Volume")
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text( text = "$${numberFormat.format(volume)}")
                        }
                    }
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.8.dp, )
                    // Low 24H
                    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp), Arrangement.SpaceBetween){
                        Column(modifier = Modifier.padding(horizontal = 10.dp,),
                            horizontalAlignment = Alignment.Start)
                        {
                            Text( text = "24H Low")
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text( text = "$${numberFormat.format(low)}")
                        }
                    }
                    // High 24H
                    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp), Arrangement.SpaceBetween){
                        Column(modifier = Modifier.padding(horizontal = 10.dp,),
                            horizontalAlignment = Alignment.Start)
                        {
                            Text( text = "24H High")
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text( text = "$${numberFormat.format(high)}")
                        }
                    }
                    // ath
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.8.dp, )
                    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp), Arrangement.SpaceBetween){
                        Column(modifier = Modifier.padding(horizontal = 10.dp,),
                            horizontalAlignment = Alignment.Start)
                        {
                            Text( text = "All-time high")
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text( text = "$${numberFormat.format(ath)}")
                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "About",
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
