package com.example.currencies.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencies.databinding.CurrencyItemBinding
import com.example.currencies.network.ITextChangeCallback

class CurrencyAdapter(private val onClick: (CurrencyItem) -> Unit) :
    RecyclerView.Adapter<CurrencyHolder>() {
    private var _items: List<CurrencyItem> = emptyList()
    private var _context: Context? = null
    var isCanChange :Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<CurrencyItem>) {
        if(!isCanChange)
            return
        _items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        _context = parent.context
        return CurrencyHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CurrencyHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (payload in payloads) {
                if (payload is CurrencyItem) {
                    val value = String.format("%.2f", payload.value)
                    holder.binding.value.setText(value)
                    payload.isSet = false
                }
            }
        }

    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        val item = _items.getOrNull(position)

        holder.setItem(item)
        val watcher = CurrencyTextWatcher(object : ITextChangeCallback {
            override fun action(
                watcher: CurrencyTextWatcher,
                value: Float?,
                position: Int,
                items: List<CurrencyItem>,
                item: CurrencyItem
            ) {
                if (item.isSet)
                    return
                val itemValue = item.get(value)

                for (i in items.indices) {
                    if (i == position)
                        continue
                    val thisItem = items[i]
                    thisItem.calc(itemValue)
                    thisItem.isSet = true
                    notifyItemChanged(i, thisItem)
                }
            }
        }, position, _items, item ?: return)
        holder.binding.value.addTextChangedListener(watcher)
        holder.binding.root.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return _items.size
    }
}