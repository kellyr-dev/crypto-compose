package com.example.cryptocompose.data.repository


import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import com.example.cryptocompose.data.remote.CoinGeckoAPI
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val api : CoinGeckoAPI
){
   suspend fun getCoins() = api.getCoinList(
       vsCurrency = "usd",
       order = "market_cap_desc",
       perPage = 20,
       page = 1,
       sparkline = false
   )
   suspend fun getCoinById(coinId: String) = api.getCoinById(coinId)
}