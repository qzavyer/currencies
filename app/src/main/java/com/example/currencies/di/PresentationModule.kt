package com.example.currencies.di

import com.example.currencies.data.CurrencyDao
import com.example.currencies.data.CurrencyDataDao
import com.example.currencies.data.CurrencyItemDao
import com.example.currencies.presentation.CurrencyItemViewModel
import com.example.currencies.presentation.CurrencyViewModelFactory
import com.example.currencies.network.APIRepository
import com.example.currencies.presentation.SettingsItemViewModel
import com.example.currencies.presentation.SettingsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    fun provideCurrencyViewModel(repository: APIRepository, currencyDao: CurrencyDao, currencyDataDao: CurrencyDataDao ): CurrencyItemViewModel {
        return CurrencyItemViewModel(repository, currencyDao, currencyDataDao)
    }

    @Provides
    fun provideCurrencyViewModelFactory(viewModel: CurrencyItemViewModel): CurrencyViewModelFactory {
        return CurrencyViewModelFactory(viewModel)
    }

    @Provides
    fun provideSettingsViewModel(repository: APIRepository, currencyItemDao: CurrencyItemDao): SettingsItemViewModel {
        return SettingsItemViewModel(repository, currencyItemDao)
    }

    @Provides
    fun provideSettingsViewModelFactory(viewModel: SettingsItemViewModel): SettingsViewModelFactory {
        return SettingsViewModelFactory(viewModel)
    }
}