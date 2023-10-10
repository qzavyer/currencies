package com.example.currencies.network

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class CurrencyResponseDto(
    @SerializedName("success") val isSuccess: Boolean?,
    @SerializedName("privacy") val privacy: String?,
    @SerializedName("source") val source: String?,
    @SerializedName("terms") val terms: String?,
    @SerializedName("timestamp") val timestamp: Long?,
    @SerializedName("quotes") val quotes: Map<String, Float>?,
) {
    override fun toString(): String {
        return timestamp.toString() + ": "
    }

    val date: OffsetDateTime
        get() {
            return OffsetDateTime.ofInstant(Instant.ofEpochSecond(timestamp ?: 0), ZoneOffset.UTC)
        }
}