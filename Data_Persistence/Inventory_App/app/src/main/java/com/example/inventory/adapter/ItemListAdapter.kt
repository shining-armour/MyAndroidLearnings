package com.example.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.data.formatPrice
import com.example.inventory.databinding.ItemListItemBinding


//This class is taking a function as a parameter
// DiffCallback is the constructor parameter for list adapter
class ItemListAdapter(private val onItemClicked : (Item) -> Unit) : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(
    DiffCallback
){

    class ItemViewHolder(private val binding: ItemListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item: Item){
            binding.apply {
                itemName.text = item.itemName
                itemPrice.text = item.formatPrice()
                itemQuantity.text = item.quantityInStock.toString()
            }
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewHolder = ItemViewHolder(ItemListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            // The constructor function should be executed now
            onItemClicked(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}