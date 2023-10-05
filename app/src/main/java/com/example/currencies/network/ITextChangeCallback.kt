package com.example.currencies.network

import com.example.currencies.presentation.CurrencyItem
import com.example.currencies.presentation.CurrencyTextWatcher

interface ITextChangeCallback {
    fun action(
        watcher: CurrencyTextWatcher,
        value: Float?,
        position: Int,
        items: List<CurrencyItem>,
        item: CurrencyItem
    )
}