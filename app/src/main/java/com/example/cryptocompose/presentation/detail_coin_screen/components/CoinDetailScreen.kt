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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.cryptocompose.presentation.detail_coin_screen.CoinDetailViewModel

@Composable
fun CoinDetailScreen(
    viewModel : CoinDetailViewModel = hiltViewModel(),
    id: String
){

    val modelCoin = viewModel.getCoin(id)
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Row( modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    AsyncImage(
                        model = state?.image,
                        contentDescription = "coin image",
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = "${state?.name} (${state?.symbol})",
                            style = MaterialTheme.typography.titleLarge
                        )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Chart",
                    style = MaterialTheme.typography.titleMedium
                    )

                Spacer(modifier = Modifier.height(40.dp))
                Text(text = "Description",
                    style = MaterialTheme.typography.titleLarge)
                Text(text = "${state?.description}")
                // composable for the market data
                // composable for info
            }
        }
    }

}