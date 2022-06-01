package com.example.offline_crypto.models

data class Property(
    val id: String,
    val name: String = "",
    val symbol: String = "",
    val image: String = "",
    val price: String = "",
    val price_last_week: String = "",
    var selected: Boolean = true,
    val horizontal: Boolean = false,
    val ranking: String,
    //val data: List<Property>? = null
) {
//    companion object Factory {
//        fun create() : FireViewModel = FireViewModel()
//        var COLLECTION = "device-configs"
//        var DOCUMENT = "alarme"
//        var FIELD_userId = "userId"
//    }
}

data class Coins(
    val coins: MutableList<Property>
)