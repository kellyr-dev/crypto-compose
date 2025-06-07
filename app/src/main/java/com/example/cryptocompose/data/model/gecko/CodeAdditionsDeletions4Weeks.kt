package com.example.cryptocompose.data.model.gecko


import com.google.gson.annotations.SerializedName

data class CodeAdditionsDeletions4Weeks(
    @SerializedName("additions")
    val additions: Int,
    @SerializedName("deletions")
    val deletions: Int
)