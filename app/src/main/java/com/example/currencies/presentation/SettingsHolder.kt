package com.example.currencies.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.currencies.data.CurrencyItem
import com.example.currencies.databinding.CurrencyListItemBinding

class SettingsHolder(private val binding: CurrencyListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: CurrencyItem?) {
        if (item == null) return

        binding.description.text = item.description
    }
}