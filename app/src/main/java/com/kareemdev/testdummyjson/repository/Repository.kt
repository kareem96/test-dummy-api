package com.kareemdev.testdummyjson.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.kareemdev.testdummyjson.data.local.ProductDao
import com.kareemdev.testdummyjson.data.local.ProductDatabase
import com.kareemdev.testdummyjson.data.local.ProductEntity
import com.kareemdev.testdummyjson.data.remote.response.Product
import com.kareemdev.testdummyjson.data.remote.response.ProductResponse
import com.kareemdev.testdummyjson.data.remote.retrofit.ApiConfig
import com.kareemdev.testdummyjson.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(application: Application) {
    private val apiService: ApiService = ApiConfig.apiService()
    private val productDao: ProductDao


    init {
        val productDb: ProductDatabase = ProductDatabase.getInstance(application)
        productDao = productDb.productDao()
    }

    /*private val _getProduct = MutableLiveData<ArrayList<Product>>()
    val getProduct: LiveData<ArrayList<Product>> = _getProduct*/
    fun getProduct() : LiveData<ArrayList<Product>>{
        val listItem = MutableLiveData<ArrayList<Product>>()
        val retrofit = apiService.getListProduct()
        retrofit.enqueue(object : Callback<ProductResponse?>{
            override fun onResponse(
                call: Call<ProductResponse?>,
                response: Response<ProductResponse?>
            ) {
                if(response.isSuccessful){
                    listItem.value = response.body()?.product
                }else{
                    val message = when (response.code()){
                        401 -> "${response.code()} : Forbidden"
                        403 -> "${response.code()} : BadRequest"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : ${response.body()}"
                    }
                    Log.d("repository", "onResponse: $message")
                }
            }

            override fun onFailure(call: Call<ProductResponse?>, t: Throwable) {
                Log.d("repository", "onFailure: ${t.message.toString()}")
            }
        })
        return listItem
    }

    fun getDetailProduct(id:Int) : MutableLiveData<Product>{
        val item = MutableLiveData<Product>()
        val retrofit = apiService.getDetailProduct(id)
        retrofit.enqueue(object : Callback<Product?>{
            override fun onResponse(
                call: Call<Product?>,
                response: Response<Product?>
            ) {
                if(response.isSuccessful){
                    item.value = response.body()
                }else{
                    val message = when (response.code()){
                        401 -> "${response.code()} : Forbidden"
                        403 -> "${response.code()} : BadRequest"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : ${response.body()}"
                    }
                    Log.d("repository", "onResponse: $message")
                }
            }

            override fun onFailure(call: Call<Product?>, t: Throwable) {
                Log.d("repository", "onFailure: ${t.message.toString()}")
            }
        })
        return item
    }


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
    ){
        CoroutineScope(Dispatchers.IO).launch {
            val entity = ProductEntity(
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
                images,
            )
            productDao.insertFav(entity)
        }
    }

    fun getAllFav(): LiveData<List<ProductEntity>> = productDao.getListFav()

    suspend fun getFav(id:Int) = productDao.getFav(id)

    fun deleteFav(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            productDao.deleteFav(id)
        }
    }
}