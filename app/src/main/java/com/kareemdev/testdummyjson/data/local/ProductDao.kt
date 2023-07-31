package com.kareemdev.testdummyjson.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("select * from product")
    fun getListFav(): LiveData<List<ProductEntity>>

    @Query("select count(*) from product where product.id = :id")
    suspend fun getFav(id:Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(product: ProductEntity)

    @Query("delete from product where product.id = :id")
    suspend fun deleteFav(id:Int): Int


}