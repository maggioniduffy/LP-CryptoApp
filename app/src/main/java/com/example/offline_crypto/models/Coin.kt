package com.example.offline_crypto.models

import com.google.gson.annotations.SerializedName

data class Coin(
    val id: String,
    val name: String = "",
    val symbol: String = "",
    var image: String = "",
    val price: String = "",
    val price_last_week: String = "",
    var selected: Boolean = true,
    val horizontal: Boolean = false,
    val ranking: String,
) {}

data class PostCoin(
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("price_last_week") val price_last_week: String?,
    @SerializedName("ranking") val ranking: String?,
    @SerializedName("image") val image: String?,
) {}

data class Coins(
    val coins: MutableList<Coin>
)