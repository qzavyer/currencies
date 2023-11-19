package com.example.currencies.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencies.data.CurrencyItem
import com.example.currencies.databinding.CurrencyListItemBinding

private const val LogTag = "SettingsAdapter"

class SettingsAdapter(private val operations: TicketOperations) :
    RecyclerView.Adapter<SettingsHolder>() {
    private var _usedItems: List<CurrencyItem> = emptyList()
    private var _unusedItems: List<CurrencyItem> = emptyList()
    private lateinit var _context: Context

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<CurrencyItem>) {
        _usedItems = items.filter {
            it.isUse
        }
        _unusedItems = items.filter {
            !it.isUse
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsHolder {
        val binding =
            CurrencyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        _context = parent.context
        return SettingsHolder(binding, operations)
    }

    override fun onBindViewHolder(holder: SettingsHolder, position: Int) {
        val item = _usedItems.getOrNull(position) ?: return
        holder.setItem(item, _unusedItems, _context)
    }

    override fun getItemCount(): Int {
        return _usedItems.size
    }
}
