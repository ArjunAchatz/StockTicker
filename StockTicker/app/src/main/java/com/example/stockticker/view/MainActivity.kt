package com.example.stockticker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.stockticker.MainActivityViewModel
import com.example.stockticker.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

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

