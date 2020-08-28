package com.example.stockticker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.stockticker.MainActivityViewModel
import com.example.stockticker.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stocks_recyclerview.adapter = StocksAdapter()

        viewModel.getUiModel().observe(this, Observer(::renderUiModel))
    }

    private fun renderUiModel(uiModel: MainActivityUiModel) {
        (stocks_recyclerview.adapter as StocksAdapter).submitList(uiModel.stocks)
    }
}

