package com.example.currencies.network

interface CurrencyCallback {
    fun action(data: CurrencyResponseDto)
    fun error(message: String)
}