package com.kareemdev.testdummyjson.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.kareemdev.testdummyjson.data.remote.response.Product
import java.util.Collections

@Entity(tableName = "product")
data class ProductEntity(
    @field:PrimaryKey
    @field:ColumnInfo("id")
    val id: Int,

    @field:ColumnInfo("title")
    val title: String,

    @field:ColumnInfo("description")
    val description: String,

    @field:ColumnInfo("price")
    val price: Long,

    @field:ColumnInfo("discountPercentage")
    val discountPercentage: Double,

    @field:ColumnInfo("rating")
    val rating: Double,

    @field:ColumnInfo("idstock")
    val stock: Long,

    @field:ColumnInfo("brand")
    val brand: String,

    @field:ColumnInfo("category")
    val category: String,

    @field:ColumnInfo("thumbnail")
    val thumbnail: String,

    @field:ColumnInfo("images")
    val images: List<String>
)

class ListStringConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<String>): String {
        // Convert List<String> to a JSON string representation that can be saved in the database
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(jsonString: String): List<String> {
        // Convert the JSON string representation from the database back to List<String>
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}