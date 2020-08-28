package com.example.stockticker.persistence

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class StockRepository : CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val stockPriceMap = hashMapOf<String, Double?>()

    @ExperimentalCoroutinesApi
    private val stockUpdateChannel = BroadcastChannel<Stock>(Channel.BUFFERED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun getStockPriceMap(): Flow<HashMap<String, Double?>> {
        val userDataHardCodedForNow = List(10) { "Stock$it" }
        userDataHardCodedForNow.forEach {
            stockPriceMap[it] = null
        }

        userDataHardCodedForNow.forEach { name ->
            launch {
                getStockTicker(name).collect { stockData ->
                    stockUpdateChannel.offer(stockData)
                }
            }
        }

        return stockUpdateChannel
            .asFlow()
            .map { stock -> stockPriceMap.apply { put(stock.name, stock.price) } }
    }

    fun cancel() = job.cancel()

    private fun getStockTicker(name: String): Flow<Stock> = flow {

        /**
         * Just a note on this :
         * `While` it is scary to see this kind of loop
         * we are adhering to flow's cooperative cancellation via delay() method;
         * so when a coroutine scope is cancelled we are guaranteed exit the loop and not leak this
         */

        while (true) {
            val timeToSleep = (Math.random() * 2000L).toLong()
            delay(timeToSleep)
            emit(Stock(name, Math.random() * 100))
        }
    }

}

