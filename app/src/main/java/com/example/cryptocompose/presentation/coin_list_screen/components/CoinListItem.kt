package com.example.cryptocompose.presentation.coin_list_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList

@Composable
fun CoinListItem(
    coin: CoinList,
    onItemClick : (CoinList) -> Unit // check
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin) }
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ){

        AsyncImage(
            model = coin.image,
            contentDescription = "coin image",
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "${coin.name} (${coin.symbol.uppercase()})",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(0.4f)
                .padding(start = 8.dp),
            maxLines = 1
        )

        Text(
            text = "$${coin.currentPrice}",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(0.2f)
                .padding(start = 8.dp),
            maxLines = 1
        )


        Box(modifier = Modifier
            .weight(0.2f)
            .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ){
            PriceChangeBadge(coin.priceChangePercentage24h)
        }

    }

}

@Composable
fun PriceChangeBadge(change: Double){

    val backgroundColor = if (change >= 0) Color(0xFFA3D2A6) else Color(0xFFD79696)
    val textColor = if (change >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
    val formatted = "${if (change >= 0) "+" else ""}${String.format("%.2f", change)}%"

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        tonalElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ){
            Text(
                text = "$formatted",
                color = textColor,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
    }
}