package com.example.currencies.presentation

import android.R
import android.R.attr.country
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencies.data.CurrencyItem
import com.example.currencies.databinding.CurrencyListItemBinding


class SettingsHolder(
    private val binding: CurrencyListItemBinding,
    private val operations: TicketOperations
) :
    RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: CurrencyItem?, values: List<CurrencyItem>, context: Context) {
        if (item == null) return

        if (item.ticket.isEmpty()) {
            val list = mutableListOf<String>()
            values.forEach {
                list.add(it.ticket)
            }
            val aa: ArrayAdapter<*> = ArrayAdapter(
                context,
                R.layout.simple_spinner_item,
                list)
            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.ticketNew.adapter = aa
            binding.ticket.visibility = View.GONE
            binding.ticketNew.visibility = View.VISIBLE
            binding.remove.visibility = View.GONE
            binding.ticketNew.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    operations.onTicketChoice("")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        } else {
            binding.ticket.visibility = View.VISIBLE
            binding.ticketNew.visibility = View.GONE
            binding.remove.visibility = View.VISIBLE
            binding.ticket.text = item.ticket
            binding.remove.setOnClickListener {
                operations.onRemoveClick(item.ticket)
            }
        }

        binding.description.text = item.description
    }
}