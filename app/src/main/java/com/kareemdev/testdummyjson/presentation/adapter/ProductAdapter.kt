package com.kareemdev.testdummyjson.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kareemdev.testdummyjson.data.remote.response.Product
import com.kareemdev.testdummyjson.databinding.ItemProductBinding
import com.kareemdev.testdummyjson.presentation.DetailActivity

class ProductAdapter:  RecyclerView.Adapter<ProductAdapter.ViewHolder>(){
    private var listItem = listOf<Product?>()

    fun setData(newDataList: List<Product?>){
        listItem = newDataList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product){
            binding.apply {
                tvTitle.text = item.title
                tvDescription.text = item.description
                Glide.with(itemView.context)
                    .load(item.thumbnail)
                    .into(ivProduct)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, item.id)
                itemView.context.startActivity(intent)
//                Toast.makeText(itemView.context, "${item.id}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        if (item != null) {
            holder.bind(item)
        }
    }
}