package com.example.currencies.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApi {
    @GET("/api/live")
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    suspend fun get(
        @Query("access_key") key: String,
        @Query("currencies")currencies: String,
        @Query("source")source: String,
        @Query("format")format: Int,
    ): CurrencyResponseDto
}