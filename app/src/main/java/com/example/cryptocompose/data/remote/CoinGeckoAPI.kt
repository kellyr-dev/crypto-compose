package com.example.cryptocompose.data.remote

import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinGeckoAPI {

    @GET("/v3/coins/markets")
    suspend fun getCoinList() : Response<List<CoinList>>

    @GET("v3/coins/{id}")
    suspend fun getCoinById(
        @Path("coinId")
        coinId : String
    ) : Response<CoinDetail>

}