package com.example.offline_crypto.models

data class Coin(
    val id: String,
    val name: String = "",
    val symbol: String = "",
    val image: String = "",
    val price: String = "",
    val price_last_week: String = "",
    var selected: Boolean = true,
    val horizontal: Boolean = false,
    val ranking: String,
) {}

data class PostCoin(
    val name: String = "",
    val symbol: String = "",
    val image: String = "",
    val price: String = "",
    val price_last_week: String = "",
) {}

data class Coins(
    val coins: MutableList<Coin>
)