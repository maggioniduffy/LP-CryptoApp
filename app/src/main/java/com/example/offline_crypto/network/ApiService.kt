package com.example.offline_crypto.network

import com.example.offline_crypto.models.Coin
import com.example.offline_crypto.models.PostCoin
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://stormy-citadel-21324.herokuapp.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
    BASE_URL
).build()

interface ApiService {
    //TODO Paginate
    @GET("api/coins")
    fun getAllData(@Query("from") from: Integer, @Query("to") to: Integer): Call<List<Coin>>

    @Headers("Content-Type: application/json")
    @POST("api/coins")
    fun addCoin(@Body coin: PostCoin): Call<PostCoin>
}


object Api {
    val retrofitService: ApiService by lazy {retrofit.create(ApiService::class.java)}
}