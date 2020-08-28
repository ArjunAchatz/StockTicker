package com.example.stockticker

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockticker.persistence.StockRepository
import com.example.stockticker.view.MainActivityUiModel
import com.example.stockticker.view.StockUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivityViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val uiModel = MutableLiveData<MainActivityUiModel>()

    init {
        viewModelScope.launch {
            stockRepository.getStockPriceMap()
                .collect { stocks ->
                    val stockUiModels = stocks.map {
                        StockUiModel(it.key, it.value?.formatPriceForUi() ?: pricePlaceholder)
                    }
                    uiModel.postValue(MainActivityUiModel(stockUiModels))
                }
        }
    }

    fun getUiModel() = uiModel as LiveData<MainActivityUiModel>

    override fun onCleared() {
        super.onCleared()
        stockRepository.cancel()
    }

    companion object {
        const val pricePlaceholder = "$-.--"
    }
}

fun Double.formatPriceForUi(): String {
    // Ideally this would consider locale and be in strings.xml
    return "$ ${BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toPlainString()}"
}