package com.example.covidtracker.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.covidtracker.R
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.global.GlobalViewModel
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GlobalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setUpUpdateWorker()
        network.setOnClickListener{
            //setUI()
            setUIForCountryHistory("Egypt")
        }

    }

    private fun setUpUpdateWorker() {
        viewModel.startUpdateWorker(5,TimeUnit.MINUTES,this)
    }

    private fun setUI() {
        viewModel.getGlobalHistoricalData().observe(this, Observer {
            it?.let {resource ->
                when(resource.status) {
                    Status.SUCCESS ->{
                        resource?.data.let {
                            it?.let {
                                textView.text = it.toString()
                            }
                        }
                    }
                    Status.ERROR->{}
                    Status.LOADING->{}
                }
            }
        })
    }

    private fun setUIForCountryHistory(countryName:String) {
        viewModel.getCountryHistoricalData(countryName).observe(this, Observer {
            it?.let {resource ->
                when(resource.status) {
                    Status.SUCCESS ->{
                        resource?.data.let {
                            it?.let {
                                textView.text = it.timeline.casesMap.toString()
                            }
                        }
                    }
                    Status.ERROR->{}
                    Status.LOADING->{}
                }
            }
        })
    }
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(RetrofitApiHelper(RetrofitBuilder.apiService),
                LocalDataBase(DatabaseBuilder.getInstance(this)))
        ).get(GlobalViewModel::class.java)
    }
}
