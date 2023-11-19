package com.example.currencies.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencyItems")
data class CurrencyItem(
    @PrimaryKey
    @ColumnInfo(name = "ticket")
    val ticket: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "is_use", defaultValue = "false")
    var isUse: Boolean,
)