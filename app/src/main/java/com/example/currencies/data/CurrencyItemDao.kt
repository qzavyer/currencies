package com.example.currencies.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyItemDao{
    @Query("SELECT * FROM currencyItems ORDER BY ticket asc")
    fun getAll(): Flow<List<CurrencyItem>>

    @Query("INSERT INTO currencyItems VALUES(:ticket, :description)")
    suspend fun insert(ticket: String, description: String)

    @Query("DELETE FROM currencyItems")
    suspend fun clear()
}