package com.example.cryptocompose.data.repository

import androidx.paging.PagingData
import com.example.cryptocompose.data.mappers.ChartRange
import com.example.cryptocompose.data.mappers.Coin
import com.example.cryptocompose.data.mappers.CoinChart
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CoinRepository {

    fun observeCoins(): Flow<List<Coin>>
    fun searchCoins(query: String): Flow<List<Coin>>
    suspend fun refreshCoins()
    suspend fun getCoinById(coinId: String): Result<CoinDetail>

    suspend fun getCoinChart(coinId: String, range: ChartRange): Result<CoinChart>

    fun getPagedCoins(): Flow<PagingData<Coin>>
}