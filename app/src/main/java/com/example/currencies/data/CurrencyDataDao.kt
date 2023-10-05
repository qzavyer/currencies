package com.example.currencies.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDataDao{
    @Query("SELECT * FROM currencyData LIMIT 1")
    fun get(): Flow<CurrencyData?>

    @Query("INSERT INTO currencyData VALUES(:date, :source)")
    suspend fun insert(date: Long, source: String)

    @Query("DELETE FROM currencyData")
    suspend fun clear()
}