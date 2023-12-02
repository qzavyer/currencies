package com.example.currencies.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencies.data.CurrencyItem
import com.example.currencies.data.CurrencyItemDao
import com.example.currencies.network.APIRepository
import com.example.currencies.network.CurrencyListCallback
import com.example.currencies.network.CurrencyListResponseDto
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
    private val _list = mutableListOf<CurrencyItem>()

    /**
     * Поток обновления состояния.
     */
    val state = _state.asStateFlow()

    /**
     * Поток обновления данных.
     */
    val dataChannel = _channel.receiveAsFlow()

    suspend fun addItem() {
        try {
            _list.add(CurrencyItem("", "test", true))
            _channel.send(_list)
            _state.value = State.Loading
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error")
        }
    }

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

    suspend fun save(ticket: String, isUse: Boolean) {
        _channel.send(listOf())
        Log.d(LogTag, "save $ticket")
        val item = _list.firstOrNull {
            it.ticket == ticket
        } ?: return
        Log.d(LogTag, "save 2")
        item.isUse = isUse
        Log.d(LogTag, "save 3")
        _channel.send(_list)
        Log.d(LogTag, "save 4")
        currencyItems.update(ticket, isUse)
        Log.d(LogTag, "save 5")
        _state.value = State.Success

        _list.forEach {
            Log.d(LogTag, "save ${it.ticket}=${it.isUse}")
        }
    }

    suspend fun save(tickets: List<String>) {
        _channel.send(listOf())
        _list.forEach {
            val ticket = it.ticket
            val isUse = tickets.any { t -> ticket.uppercase() == t.trim().uppercase() }
            it.isUse = isUse
            currencyItems.update(ticket, isUse)
        }
        _channel.send(_list)
        _state.value = State.Success
    }

    private suspend fun loadFromNetwork() {
        try {
            repository.getCurrencies(object : CurrencyListCallback {
                override fun action(data: CurrencyListResponseDto) {
                    viewModelScope.launch {
                        try {
                            if (data.isSuccess == true) {
                                val list = mutableListOf<CurrencyItem>()
                                data.symbols?.forEach {
                                    Log.d(LogTag, "data.symbols?.forEach ${it.key}=${it.value}")
                                    val item = CurrencyItem(it.key, it.value, false)
                                    list.add(item)
                                    currencyItems.insert(it.key, it.value)
                                }
                                list.sortBy {
                                    it.ticket
                                }
                                list.add(CurrencyItem("", "", true))
                                Log.d(LogTag, "loadFromNetwork ${list.size}")
                                _channel.send(list)
                                _state.value = State.Success
                            } else {
                                _state.value = State.Error("Network load error")
                            }
                        } catch (t: Throwable) {
                            Log.e(LogTag, t.message ?: "error")
                        }
                    }
                }

                override fun error(message: String) {
                    _state.value = State.Error(message)
                }
            })
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error", t)
        }
    }

    private suspend fun loadFromDb() {
        try {
            _list.clear()
            _list.add(CurrencyItem("", "empty", true))
            val currencyItems = currencyItems.getAll().firstOrNull()
            if (currencyItems == null || currencyItems.isEmpty()) {
                loadFromNetwork()
                return
            }
            currencyItems.forEach {
                _list.add(it)
            }
            _list.sortBy {
                it.ticket
            }
            _channel.send(_list)
            _state.value = State.Success
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error", t)
        }
    }
}