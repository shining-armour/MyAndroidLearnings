package com.example.mars_photos_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mars_photos_app.databinding.GridItemViewBinding
import com.example.mars_photos_app.network.MarsPhoto

class MarsPhotosAdapter: ListAdapter<MarsPhoto, MarsPhotosAdapter.MarsPhotoViewHolder>(DiffCallback) {

    class MarsPhotoViewHolder(private var binding:GridItemViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(marsPhotoWithId: MarsPhoto){
            binding.singleMarsPhoto = marsPhotoWithId
            binding.executePendingBindings()  //any update is executed immediately
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        return MarsPhotoViewHolder(GridItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }

    // You will compare two Mars photo objects inside this implementation.
    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {

        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return  oldItem.imgUrl == newItem.imgUrl
        }

    }

}