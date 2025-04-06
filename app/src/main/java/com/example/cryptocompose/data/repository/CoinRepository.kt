package com.example.cryptocompose.data.repository

import com.example.cryptocompose.data.model.Coin
import com.example.cryptocompose.data.model.DetailCoin

interface CoinRepository {

    // function call for each api request
    // if I am using cache function to insert in a database

    suspend fun getCoins() : List<Coin>

    suspend fun getCoingById(coinId: String): DetailCoin

}