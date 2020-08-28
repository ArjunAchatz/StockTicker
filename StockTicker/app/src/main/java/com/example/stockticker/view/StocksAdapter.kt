package com.example.stockticker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stockticker.R
import kotlinx.android.synthetic.main.stock_list_item.view.*

class StocksAdapter : ListAdapter<StockUiModel, StockViewHolder>(
    object : DiffUtil.ItemCallback<StockUiModel>() {
        override fun areItemsTheSame(
            oldItem: StockUiModel,
            newItem: StockUiModel
        ) = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: StockUiModel,
            newItem: StockUiModel
        ) = oldItem == newItem

    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StockViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.stock_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(uiModel: StockUiModel) {
        itemView.stock_name.text = uiModel.name
        itemView.stock_price.text = uiModel.price
    }

}