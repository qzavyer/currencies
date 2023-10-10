package com.example.currencies.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencies.data.CurrencyItem
import com.example.currencies.databinding.CurrencyListItemBinding

class SettingsAdapter : RecyclerView.Adapter<SettingsHolder>() {
    private var _items: List<CurrencyItem> = emptyList()
    private var _context: Context? = null
    var isCanChange: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<CurrencyItem>) {
        if (!isCanChange)
            return
        _items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsHolder {
        val binding =
            CurrencyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        _context = parent.context
        return SettingsHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsHolder, position: Int) {
        val item = _items.getOrNull(position)

        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return _items.size
    }
}