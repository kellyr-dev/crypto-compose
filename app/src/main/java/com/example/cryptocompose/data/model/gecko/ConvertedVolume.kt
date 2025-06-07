package com.example.cryptocompose.data.model.gecko


import com.google.gson.annotations.SerializedName

data class ConvertedVolume(
    @SerializedName("btc")
    val btc: Int,
    @SerializedName("eth")
    val eth: Int,
    @SerializedName("usd")
    val usd: Int
)