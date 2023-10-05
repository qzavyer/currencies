package com.example.currencies.network

import android.util.Log
import com.example.currencies.BuildConfig

private const val LogTag = "APIRepository"

class APIRepository {
    suspend fun getData(callback: CurrencyCallback) {
        try {
            val res = RetrofitInstance.searchLoginApi.get(
                BuildConfig.API_KEY,
                "AMD,GEL,RUB,EUR,TRY",
                "USD",
                1
            )
            callback.action(res)
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error")
            callback.error(t.message ?: "Network error")
        }
    }
}

