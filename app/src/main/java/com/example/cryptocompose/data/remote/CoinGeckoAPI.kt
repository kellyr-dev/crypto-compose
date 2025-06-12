package com.example.cryptocompose.data.remote

import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinGeckoAPI {

    @GET("/markets")
    suspend fun getCoinList() : Response<List<CoinList>>

    @GET("/{id}")
    suspend fun getCoinById(
        @Path("id")
        coinId : String
    ) : Response<CoinDetail>

}