package com.example.currencies.presentation

interface ITextChangeCallback {
    fun action(
        watcher: CurrencyTextWatcher,
        value: Float?,
        position: Int,
        items: List<CurrencyValueItem>,
        item: CurrencyValueItem
    )
}