package com.example.currencies.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencies.data.CurrencyDao
import com.example.currencies.data.CurrencyDataDao
import com.example.currencies.network.APIRepository
import com.example.currencies.network.CurrencyCallback
import com.example.currencies.network.CurrencyResponseDto
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

private const val LogTag = "CurrencyItemViewModel"

class CurrencyItemViewModel(
    private val repository: APIRepository,
    private val currencies: CurrencyDao,
    private val currencyData: CurrencyDataDao
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Unready)
    private val _lastDate = MutableStateFlow<OffsetDateTime?>(null)
    private val _channel = Channel<List<CurrencyValueItem>>()

    /**
     * Поток обновления состояния.
     */
    val state = _state.asStateFlow()

    /**
     * Поток обновления даты.
     */
    val date = _lastDate.asStateFlow()

    /**
     * Поток обновления данных.
     */
    val dataChannel = _channel.receiveAsFlow()

    fun updateData() {
        Log.d(LogTag, "START")
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                val dataDao = currencyData.get().firstOrNull()
                _lastDate.value = dataDao?.date
                if (dataDao?.date == null || dataDao.date.plusDays(1) < OffsetDateTime.now()) {
                    Log.d(LogTag, "${dataDao?.date}")
                    loadFromNetwork(true)
                } else {
                    loadFromDb(true)
                }
            } catch (t: Throwable) {
                Log.e(LogTag, t.message ?: "error", t)
                _state.value = State.Error(t.message ?: "Error")
            }
        }
    }

    private val order: Map<String, Int> =
        mapOf("USD" to 1, "EUR" to 2, "RUB" to 3, "GEL" to 4, "TRY" to 5, "AMD" to 6)

    private suspend fun loadFromDb(loadFromNetworkIfException: Boolean) {
        try {
            val list = mutableListOf<CurrencyValueItem>()
            list.add(
                CurrencyValueItem("USD", 1f, "")
            )
            val currencyValues = currencies.getAll().firstOrNull()
            if (currencyValues == null) {
                if (loadFromNetworkIfException) {
                    loadFromNetwork(false)
                }
                return
            }
            currencyValues.forEach {
                list.add(CurrencyValueItem(it.name, it.value, ""))
            }
            list.sortBy {
                order[it.ticket]
            }
            _channel.send(list)
            _state.value = State.Success
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error", t)
            if (loadFromNetworkIfException) {
                loadFromNetwork(false)
            }
        }
    }

    private suspend fun loadFromNetwork(loadFromDbIfException: Boolean) {
        try {
            Log.i(LogTag, "INSERT")
            repository.getData(object : CurrencyCallback {
                override fun action(data: CurrencyResponseDto) {
                    viewModelScope.launch {
                        if (data.isSuccess == true) {
                            val source = data.source ?: "USD"
                            _lastDate.value = data.date
                            val list = mutableListOf<CurrencyValueItem>()
                            list.add(
                                CurrencyValueItem(
                                    source,
                                    1f,
                                    ""
                                )
                            )
                            currencyData.clear()
                            currencyData.insert(data.timestamp ?: 0, source)
                            currencies.clear()
                            data.quotes?.forEach {
                                val item = CurrencyValueItem(it.key, it.value, source)
                                list.add(item)
                                currencies.insert(item.ticket, item.course)
                            }
                            list.sortBy {
                                order[it.ticket]
                            }
                            Log.d(LogTag, "${list.size}")
                            _channel.send(list)
                            _state.value = State.Success
                        } else {
                            _state.value = State.Error("Network load error")
                            if (loadFromDbIfException) {
                                loadFromDb(false)
                            }
                        }
                    }
                }

                override fun error(message: String) {
                    _state.value = State.Error(message)
                    if (loadFromDbIfException) {
                        viewModelScope.launch {
                            loadFromDb(false)
                        }
                    }
                }
            })
        } catch (t: Throwable) {
            Log.e(LogTag, t.message ?: "error", t)
            if (loadFromDbIfException) {
                loadFromDb(false)
            }
        }
    }
}