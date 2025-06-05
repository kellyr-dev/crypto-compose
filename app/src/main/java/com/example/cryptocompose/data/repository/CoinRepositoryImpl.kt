package com.example.cryptocompose.data.repository


import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import retrofit2.Response
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api : CoinGeckoAPI
) : CoinRepository {
    override suspend fun getCoins(): Response<List<CoinList>> {
        return api.getCoinList()
    }

    override suspend fun getCoinById(coinId: String): Response<CoinDetail> {
        return api.getCoinById(coinId)
    }
}