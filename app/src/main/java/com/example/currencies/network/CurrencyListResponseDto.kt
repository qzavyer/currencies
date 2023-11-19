package com.example.currencies.network

import com.google.gson.annotations.SerializedName

data class CurrencyListResponseDto(
    @SerializedName("success") val isSuccess: Boolean?,
    @SerializedName("currencies") val symbols: Map<String, String>?,
)