package com.example.currencies.presentation

sealed class State {
    object Success : State()
    object Loading : State()
    object Unready : State()
    data class Error(val message: String) : State()
}