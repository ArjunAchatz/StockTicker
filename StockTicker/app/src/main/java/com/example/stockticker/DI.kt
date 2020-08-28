package com.example.stockticker

import com.example.stockticker.persistence.StockRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { StockRepository() }

    viewModel { MainActivityViewModel(StockRepository()) }

}