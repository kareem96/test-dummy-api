package com.kareemdev.testdummyjson.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kareemdev.testdummyjson.data.remote.response.Product
import com.kareemdev.testdummyjson.repository.Repository

class DetailViewModel(application: Application): ViewModel() {
    private val repository = Repository(application)

    val detail = MutableLiveData<Product>()

    fun insertFav(
        id: Int,
        title: String,
        description: String,
        price: Long,
        discountPercentage: Double,
        rating: Double,
        stock: Long,
        brand: String,
        category: String,
        thumbnail: String,
        images: List<String>,
    )= repository.insertFav(
        id,
        title,
        description,
        price,
        discountPercentage,
        rating,
        stock,
        brand,
        category,
        thumbnail,
        images
    )

    fun getDetailProduct(id: Int) = repository.getDetailProduct(id)

    suspend fun getFav(id:Int) = repository.getFav(id)

    fun getListFav() = repository.getAllFav()

    fun deleteFav(id:Int) = repository.deleteFav(id)
}