package com.kareemdev.testdummyjson.data.remote.retrofit

import com.kareemdev.testdummyjson.data.remote.response.Product
import com.kareemdev.testdummyjson.data.remote.response.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    fun getListProduct(): Call<ProductResponse>

    @GET("products/{id}")
    fun getDetailProduct(
        @Path("id") id: Int
    ): Call<Product>
}