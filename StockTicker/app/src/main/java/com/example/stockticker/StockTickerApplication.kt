package com.example.stockticker

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// parijat@fireworkhq.com ;
// deborah@fireworkhq.com

class StockTickerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StockTickerApplication)
            modules(appModules)
        }
    }

}