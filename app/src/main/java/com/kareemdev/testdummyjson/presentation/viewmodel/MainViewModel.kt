package com.kareemdev.testdummyjson.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kareemdev.testdummyjson.repository.Repository

class MainViewModel(application: Application): ViewModel() {
    private val repository = Repository(application)
    fun getProduct() = repository.getProduct()

}