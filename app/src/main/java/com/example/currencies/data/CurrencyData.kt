package com.example.currencies.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity(tableName = "currencyData")
data class CurrencyData(
    @PrimaryKey
    @ColumnInfo(name = "date")
    var dateLong: Long,
    @ColumnInfo(name = "source")
    val source: String
)
{
    var date : OffsetDateTime
    get() {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(dateLong), ZoneOffset.UTC)
    }
    set(value){
        dateLong = value.toEpochSecond()
    }
}
