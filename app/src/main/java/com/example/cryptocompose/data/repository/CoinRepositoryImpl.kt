package com.example.cryptocompose.data.repository

import com.example.cryptocompose.data.model.Coin
import com.example.cryptocompose.data.model.DetailCoin
import com.example.cryptocompose.data.remote.CoinPaprikaAPI

class CoinRepositoryImpl @Inject constructor(
    private val api : CoinPaprikaAPI
) : CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        return api.getCoins()
    }

    override suspend fun getCoingById(coinId: String): DetailCoin {
        return api.getCoinById(coinId)
    }
}