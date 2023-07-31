package com.kareemdev.testdummyjson.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kareemdev.testdummyjson.R
import com.kareemdev.testdummyjson.data.remote.response.Product
import com.kareemdev.testdummyjson.databinding.ActivityDetailBinding
import com.kareemdev.testdummyjson.presentation.adapter.ImageAdapter
import com.kareemdev.testdummyjson.presentation.viewmodel.DetailViewModel
import com.kareemdev.testdummyjson.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var isChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Detail"

        viewModel = obtainFactory(this as AppCompatActivity)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        viewModel.getDetailProduct(id).observe(this) { response ->
            contentDetail(response)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.getFav(id)
            withContext(Dispatchers.Main){
                isChecked = if (count > 0){
                    isFav(true)
                    true
                }else{
                    isFav(false)
                    false
                }
            }
        }
    }

    private fun obtainFactory(activity: AppCompatActivity): DetailViewModel {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun contentDetail(item: Product) {
        val adapter = ImageAdapter(item.images)
        binding.rvImage.layoutManager = GridLayoutManager(this, 3)
        binding.rvImage.adapter = adapter

        binding.apply {
            tvTitleDetail.text = item.title
            tvDescriptionDetail.text = item.description
            Glide.with(this@DetailActivity)
                .load(item.thumbnail)
                .into(ivProduct)
        }

        binding.fabFav.setOnClickListener {
            isChecked = !isChecked
            if(isChecked){
                Toast.makeText(this, "success into favorite", Toast.LENGTH_SHORT).show()
                viewModel.insertFav(
                    item.id,
                    item.title,
                    item.description,
                    item.price,
                    item.discountPercentage,
                    item.rating,
                    item.stock,
                    item.brand,
                    item.category,
                    item.thumbnail,
                    item.images
                )
            }else{
                viewModel.deleteFav(item.id)
                Toast.makeText(this, "cancel into favorite", Toast.LENGTH_SHORT).show()
            }
            isFav(isChecked)
        }
    }

    private fun isFav(fav: Boolean){
        if(fav){
            binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
        }else binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
