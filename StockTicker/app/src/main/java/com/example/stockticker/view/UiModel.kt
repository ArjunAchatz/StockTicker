package com.example.stockticker.view

data class MainActivityUiModel(val stocks: List<StockUiModel>)

data class StockUiModel(val name: String, val price: String)