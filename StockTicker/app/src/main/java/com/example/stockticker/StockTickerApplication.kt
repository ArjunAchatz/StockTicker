package com.example.stockticker

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StockTickerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StockTickerApplication)
            modules(appModules)
        }
    }

}
