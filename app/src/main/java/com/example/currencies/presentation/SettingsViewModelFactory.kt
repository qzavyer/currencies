package com.example.currencies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingsViewModelFactory(private val viewModel: SettingsItemViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsItemViewModel::class.java)) {
            return viewModel as T
        }

        throw java.lang.IllegalArgumentException("Unknown model")
    }
}