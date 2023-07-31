package com.kareemdev.testdummyjson.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val product: ArrayList<Product>,
)

data class Product(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("discountPercentage")
    val discountPercentage: Double,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("idstock")
    val stock: Long,

    @SerializedName("brand")
    val brand: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("images")
    val images: List<String>
)