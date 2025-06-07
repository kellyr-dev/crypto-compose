package com.example.cryptocompose.presentation.coin_list_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList

@Composable
fun CoinListItem(
    coin: CoinDetail,
    onItemClick : (CoinDetail) -> Unit
){

    var change = coin.marketData.athChangePercentage

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick }
            .padding(20.dp),

        horizontalArrangement = Arrangement.SpaceBetween
    ){

        AsyncImage(
            model = coin.image,
            contentDescription = "coin image",
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${coin.marketData.currentPrice}",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${coin.marketData.athChangePercentage}",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            color = if(change.usd > 0) Color.Green else Color.Red
        )

    }


}