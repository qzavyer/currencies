package com.example.currencies.presentation

import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import com.example.currencies.databinding.CurrencyItemBinding

class CurrencyHolder(private val binding: CurrencyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun start(watcher: TextWatcher) {
        binding.value.addTextChangedListener(watcher)
    }

    fun setItem(item: CurrencyValueItem?) {
        if (item == null) return

        binding.value.clearComposingText()
        binding.currencyName.text = item.ticket
        if (item.value == null)
            binding.value.setText("")
        else
            binding.value.setText(item.value.toString())
    }

    fun updateValue(value: Float?) {
        val text = String.format("%.2f", value)
        binding.value.setText(text)
    }
}

