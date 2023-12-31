package com.example.currencies.presentation

import android.text.Editable
import android.text.TextWatcher

class CurrencyTextWatcher(
    private val callback: ITextChangeCallback,
    private val position: Int,
    private val items: List<CurrencyValueItem>,
    private val item: CurrencyValueItem
) : TextWatcher {
    private val watcher = this
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        val value = p0?.toString() ?: return
        if (value == "")
            return
        callback.action(watcher, value.replace(",", ".").toFloat(), position, items, item)
    }
}