package com.example.currencies.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Currency::class, CurrencyData::class, CurrencyItem::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currency(): CurrencyDao
    abstract fun currencyData(): CurrencyDataDao
    abstract fun currencyList(): CurrencyItemDao
}