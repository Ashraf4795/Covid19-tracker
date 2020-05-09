package com.example.covidtracker.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.covidtracker.R
import com.example.covidtracker.core.COUNTRY_DATA_EXTRA_KEY
import com.example.covidtracker.core.GLOBAL_DATA_EXTRA_KEY
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.global.GlobalViewModel
import com.example.covidtracker.main.MainActivity
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.fragment_global.*
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: GlobalViewModel
    private lateinit var globalData: GlobalData
    val DISPLAY_DURATION:Long = 4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpGlobalViewModel()

    }

    override fun onResume() {
        super.onResume()
        getData()
        viewModel.startUpdateWorker(15,TimeUnit.MINUTES,applicationContext)
    }
    private fun getData() {
        viewModel.getGlobalDataWithCountriesData().observe(this,Observer{
            when(it.status){
                Status.SUCCESS-> {
                    if(it.data !=null){
                        goToMain()
                    }
                }
                Status.ERROR ->{

                }
                Status.LOADING ->{

                }
            }
        })
    }

    private fun goToMain(){
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUpGlobalViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                RetrofitApiHelper(RetrofitBuilder.apiService),
                LocalDataBase(DatabaseBuilder.getInstance(this))
            )
        ).get(GlobalViewModel::class.java)
    }
}
