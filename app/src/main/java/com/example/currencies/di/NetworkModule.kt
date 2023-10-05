package com.example.currencies.di

import com.example.currencies.network.APIRepository
import dagger.Module
import dagger.Provides

@Module
class NetworkModule{
    @Provides
    fun provideRepository(): APIRepository {
        return APIRepository()
    }
}