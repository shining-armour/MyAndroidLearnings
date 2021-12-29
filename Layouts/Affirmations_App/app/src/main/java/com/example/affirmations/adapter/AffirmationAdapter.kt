package com.example.affirmations.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

var findViewByIdCounter = 0
var numberOfTimesOnCreateViewHolderIsCalled = 0

class AffirmationAdapter(private val context: Context, private val data: List<Affirmation>) : RecyclerView.Adapter<AffirmationAdapter.AffirmationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AffirmationViewHolder {
     numberOfTimesOnCreateViewHolderIsCalled = numberOfTimesOnCreateViewHolderIsCalled.plus(1)
     Log.d("In onCreateVH", "View Holder created for ${ if (numberOfTimesOnCreateViewHolderIsCalled>1) "$numberOfTimesOnCreateViewHolderIsCalled times" else "$numberOfTimesOnCreateViewHolderIsCalled time"}")
     val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
     return AffirmationViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: AffirmationViewHolder, position: Int) {
        Log.d("In onBindVH", "Binding data to VH number #${position+1}")
        val item = data[position]
        holder.textView.text = context.resources.getString(item.StringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
    }

    override fun getItemCount():Int {
//        Log.d("In getItemCount", data.size.toString())
        return data.size
    }

    class AffirmationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val textView: TextView = itemView.findViewById(R.id.item_title)
         val imageView: ImageView = itemView.findViewById(R.id.item_image)
         init {
             findViewByIdCounter = findViewByIdCounter.plus(2)
             Log.d("In ViewHolder class", "findViewById called ${ if (findViewByIdCounter>1) "$findViewByIdCounter times" else "$findViewByIdCounter time"}")
         }
    }

}