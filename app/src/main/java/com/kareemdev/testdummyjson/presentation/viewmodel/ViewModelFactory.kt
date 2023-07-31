package com.kareemdev.testdummyjson.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application) as T

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application) as T

            else -> throw IllegalArgumentException("Unknown ViewModel Class" + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory = instance ?: synchronized(this){
            instance ?: ViewModelFactory(application)
        }.also { instance }
    }
}