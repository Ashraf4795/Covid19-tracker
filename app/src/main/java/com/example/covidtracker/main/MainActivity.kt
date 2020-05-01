package com.example.covidtracker.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.covidtracker.R
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.AppDataBase
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.utils.Status.SUCCESS
import com.example.covidtracker.utils.Status.ERROR
import com.example.covidtracker.utils.Status.LOADING
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        //setUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(RetrofitApiHelper(RetrofitBuilder.apiService),
                LocalDataBase(DatabaseBuilder.getInstance(this)))
        ).get(MainViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getGlobalData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                       resource.data?.let { globalData ->viewModel.InsertGlobalDat(globalData)
                           viewModel.getGlobalDataFromDb().observe(this, Observer {
                               Log.d("ddddd","ddddddddddddddddddddddd")

                               it?.let { resource ->
                                   when (resource.status) {
                                       SUCCESS -> {
                                           resource.data?.let { globalData ->textView.text=globalData.toString()
                                               Log.d("dataaaaaaa",globalData.toString())


                                           }
                                       }
                                       ERROR -> {
                                           Log.e("Main", it.message)
                                           Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                       }
                                       LOADING -> {
                                           Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                       }
                                   }
                               }
                           })
                        }
                    }
                    ERROR -> {
                        Log.e("Main", it.message)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

    }
}
