package com.kareemdev.testdummyjson.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kareemdev.testdummyjson.R
import com.kareemdev.testdummyjson.databinding.ActivityMainBinding
import com.kareemdev.testdummyjson.presentation.adapter.ProductAdapter
import com.kareemdev.testdummyjson.presentation.viewmodel.MainViewModel
import com.kareemdev.testdummyjson.presentation.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Test Dummy"

        mAdapter = ProductAdapter()
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mAdapter
        }
        viewModel = obtainFactory(this as AppCompatActivity)
        observeData()
    }

    private fun observeData() {

        viewModel.getProduct().observe(this) { response ->
            response.let {
                mAdapter.setData(response)
            }
        }

    }

    private fun obtainFactory(appCompatActivity: AppCompatActivity): MainViewModel {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(appCompatActivity.application)
        return ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_fav -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> true
        }
    }
}
