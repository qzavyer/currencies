package com.example.currencies.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Currency::class, CurrencyData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyDataDao(): CurrencyDataDao
}