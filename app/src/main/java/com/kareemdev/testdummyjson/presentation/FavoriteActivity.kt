package com.kareemdev.testdummyjson.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareemdev.testdummyjson.R
import com.kareemdev.testdummyjson.data.local.ProductEntity
import com.kareemdev.testdummyjson.data.remote.response.Product
import com.kareemdev.testdummyjson.databinding.ActivityFavoriteBinding
import com.kareemdev.testdummyjson.presentation.adapter.ProductAdapter
import com.kareemdev.testdummyjson.presentation.viewmodel.DetailViewModel
import com.kareemdev.testdummyjson.presentation.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var mAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Favorite"

        mAdapter = ProductAdapter()
        binding.rvFav.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = mAdapter
        }
        viewModel = obtainFactory(this as AppCompatActivity)
        viewModel.getListFav().observe(this){
            val listFav = mapFavList(it)
            if(it != null && it.isNotEmpty()){
                showTextDummy(false)
                mAdapter.setData(listFav)
            } else{
                showTextDummy(true)
                mAdapter.setData(listFav)
            }
        }
    }

    private fun showTextDummy(state: Boolean) {
        binding.tvNoneFavorite.isVisible = state
    }

    private fun mapFavList(it: List<ProductEntity>?): ArrayList<Product> {
        val list = ArrayList<Product>()
        if (it != null) {
            for(product in it){
                val listMap = Product(
                    product.id,
                    product.title,
                    product.description,
                    product.price,
                    product.discountPercentage,
                    product.rating,
                    product.stock,
                    product.brand,
                    product.category,
                    product.thumbnail,
                    product.images
                )
                list.add(listMap)
            }
        }
        return list
    }

    private fun obtainFactory(appCompatActivity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(appCompatActivity.application)
        return ViewModelProvider(appCompatActivity, factory)[DetailViewModel::class.java]
    }
}