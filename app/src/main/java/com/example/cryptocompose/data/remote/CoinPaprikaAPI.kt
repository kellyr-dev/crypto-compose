package com.example.cryptocompose.data.remote

import com.example.cryptocompose.data.model.Coin
import com.example.cryptocompose.data.model.DetailCoin
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaAPI {

    //@GET("/v1/coins")
    @GET("/v1/coins")
    suspend fun getCoins() : List<Coin>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(
        @Path("coinId") coinId : String) : DetailCoin

}