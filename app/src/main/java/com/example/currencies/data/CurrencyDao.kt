package com.example.currencies.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao{
    @Query("SELECT * FROM currencies ORDER BY name desc")
    fun getAll(): Flow<List<Currency>>

    @Query("INSERT INTO currencies VALUES(:name, :value)")
    suspend fun insert(name: String, value: Float)

    @Query("UPDATE currencies SET value = :value WHERE name like :name")
    suspend fun update(name: String, value: Float): Int

    @Query("DELETE FROM currencies")
    suspend fun clear()
}