package com.example.currencies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrencyViewModelFactory(private val viewModel: CurrencyItemViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyItemViewModel::class.java)) {
            return viewModel as T
        }

        throw java.lang.IllegalArgumentException("Unknown model")
    }
}