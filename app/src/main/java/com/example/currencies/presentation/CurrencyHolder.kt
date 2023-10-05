package com.example.currencies.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.currencies.databinding.CurrencyItemBinding

class CurrencyHolder(val binding: CurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: CurrencyItem?) {
        if (item == null) return

        binding.value.clearComposingText()
        binding.currencyName.text = item.ticket
        if (item.value == null)
            binding.value.setText("")
        else
            binding.value.setText(item.value.toString())
    }
}