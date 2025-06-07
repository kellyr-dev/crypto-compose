package com.example.cryptocompose.data.repository

import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import retrofit2.Response

interface CoinRepository {


    suspend fun getCoins() : Response<List<CoinList>>

    suspend fun getCoinById(coinId: String): Response<CoinDetail>
}