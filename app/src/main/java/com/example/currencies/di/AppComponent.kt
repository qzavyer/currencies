package com.example.currencies.di

import com.example.currencies.presentation.CurrencyViewModelFactory
import com.example.currencies.presentation.SettingsViewModelFactory
import dagger.Component

@Component(
    modules = [NetworkModule::class, DataModule::class, PresentationModule::class]
)
interface AppComponent {
    fun currencyViewModelFactory(): CurrencyViewModelFactory
    fun settingsViewModelFactory(): SettingsViewModelFactory
}
