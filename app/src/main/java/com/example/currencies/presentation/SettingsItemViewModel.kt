package com.example.currencies.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencies.data.CurrencyItem
import com.example.currencies.data.CurrencyItemDao
import com.example.currencies.network.APIRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val LogTag = "SettingsItemViewModel"

class SettingsItemViewModel(
    private val repository: APIRepository,
    private val currencyItems: CurrencyItemDao
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Unready)
    private val _channel = Channel<List<CurrencyItem>>()

    /**
     * Поток обновления состояния.
     */
    val state = _state.asStateFlow()

    /**
     * Поток обновления данных.
     */
    val dataChannel = _channel.receiveAsFlow()

    fun updateData() {
        Log.d(LogTag, "START")
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                loadFromDb()
            } catch (t: Throwable) {
                Log.e(LogTag, t.message ?: "error", t)
                _state.value = State.Error(t.message ?: "Error")
            }
        }
    }

    private suspend fun loadFromDb() {
        try {
            val list = mutableListOf<CurrencyItem>()
            val currencyItems = currencyItems.getAll().firstOrNull() ?: return
            currencyItems.forEach {
                list.add(it)
            }
            list.sortBy {
                it.ticket
            }
            _channel.send(list)
            _state.value = State.Success
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error", t)
        }
    }
}