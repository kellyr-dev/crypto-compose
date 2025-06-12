package com.example.cryptocompose.data.remote

import com.example.cryptocompose.data.model.gecko.CoinDetail
import com.example.cryptocompose.data.model.gecko.CoinList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoAPI {

    @GET("coins/markets")
    suspend fun getCoinList(
        @Query("vs_currency") vsCurrency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sparkline") sparkline: Boolean
    ) : Response<List<CoinList>>

    @GET("/{id}")
    suspend fun getCoinById(
        @Path("id")
        coinId : String
    ) : Response<CoinDetail>

}