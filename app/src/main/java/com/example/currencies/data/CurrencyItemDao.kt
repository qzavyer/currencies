package com.example.currencies.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyItemDao{
    @Query("SELECT * FROM currencyItems ORDER BY ticket asc")
    fun getAll(): Flow<List<CurrencyItem>>

    @Query("INSERT INTO currencyItems (ticket, description, is_use) VALUES(:ticket, :description, 'FALSE')")
    suspend fun insert(ticket: String, description: String)

    @Query("UPDATE currencyItems SET is_use = :isUse WHERE ticket = :ticket")
    suspend fun update(ticket: String, isUse: Boolean)

    @Query("DELETE FROM currencyItems")
    suspend fun clear()
}