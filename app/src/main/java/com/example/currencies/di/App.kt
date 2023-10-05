package com.example.currencies.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.currencies.data.AppDatabase

class App : Application() {
    private lateinit var _db: AppDatabase

    val db: AppDatabase
        get() {
            return _db
        }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this

        _db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "rdb").build()
        initDI()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder().dataModule(DataModule(this))
            .build()
    }
}