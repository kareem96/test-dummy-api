package com.kareemdev.testdummyjson.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kareemdev.testdummyjson.databinding.ItemImageBinding

class ImageAdapter(private val stringImage: List<String>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    inner class ViewHolder (private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:String){
            binding.apply {
                Glide.with(itemView.context)
                    .load(item)
                    .into(ivImageString)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val view = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        val item = stringImage[position]
        if(item != null){
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = stringImage.size
}