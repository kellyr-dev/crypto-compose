package com.example.cryptocompose.data.repository

import com.example.cryptocompose.data.mappers.Coin
import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CoinRepository {

    fun observeCoins(): Flow<List<Coin>>
    suspend fun refreshCoins()
    suspend fun getCoinById(coinId: String): Result<CoinDetail>
}