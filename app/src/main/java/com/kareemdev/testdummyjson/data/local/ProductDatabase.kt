package com.kareemdev.testdummyjson.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProductEntity::class], exportSchema = false, version = 1)
@TypeConverters(ListStringConverter::class)
abstract class ProductDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
        private var instance: ProductDatabase? = null
        fun getInstance(context: Context): ProductDatabase = instance ?: synchronized(this){
            Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
            "product.db"
            ).build().apply {
                instance = this
            }
        }
    }
}