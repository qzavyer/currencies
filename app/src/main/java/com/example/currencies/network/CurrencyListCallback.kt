package com.example.currencies.network

interface CurrencyListCallback {
    fun action(data: CurrencyListResponseDto)
    fun error(message: String)
}