package com.example.currencies.di

import com.example.currencies.data.CurrencyDao
import com.example.currencies.data.CurrencyDataDao
import dagger.Module
import dagger.Provides

@Module
class DataModule(private val application: App) {
    @Provides
    fun provideCurrencies(): CurrencyDao {
        return application.db.currencyDao()
    }

    @Provides
    fun provideCurrencyData(): CurrencyDataDao {
        return application.db.currencyDataDao()
    }
}

