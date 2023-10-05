package com.example.currencies.di

import com.example.currencies.presentation.CurrencyViewModelFactory
import dagger.Component

@Component(
    modules = [NetworkModule::class, DataModule::class, PresentationModule::class]
)
interface AppComponent {
    fun currencyViewModelFactory(): CurrencyViewModelFactory
}
