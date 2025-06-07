package com.example.cryptocompose.data.model.gecko


import com.google.gson.annotations.SerializedName

data class Localization(
    @SerializedName("de")
    val de: String,
    @SerializedName("en")
    val en: String
)