package com.example.currencies.network

import android.util.Log
import com.example.currencies.BuildConfig

private const val LogTag = "APIRepository"

class APIRepository {
    suspend fun getData(callback: CurrencyCallback, tickets: String) {
        try {
            val res = RetrofitInstance.searchLoginApi.getQuotes(
                BuildConfig.API_KEY,
                /*"AMD,GEL,RUB,EUR,TRY"*/tickets,
                "USD",
                1
            )
            callback.action(res)
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error")
            callback.error(t.message ?: "Network error")
        }
    }

    suspend fun getCurrencies(callback: CurrencyListCallback){
        try {
            val res = RetrofitInstance.searchLoginApi.getCurrencies(BuildConfig.API_KEY)
            callback.action(res)
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error")
            callback.error(t.message ?: "Network error")
        }
    }
}

