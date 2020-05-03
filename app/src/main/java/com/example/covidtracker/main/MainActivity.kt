package com.example.covidtracker.main

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.covidtracker.R
import com.example.covidtracker.core.INTERVAL_KEY
import com.example.covidtracker.core.PREFERENCE_KEY
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.local.shardPreference.SharedPreferenceBuilder
import com.example.covidtracker.core.models.SubscripEntity
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.core.notification.NotificationCreator
import com.example.covidtracker.global.GlobalViewModel
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
           // setUIForCountryHistory("Egypt")
            startNotification()

        }

    }

    private fun setUpUpdateWorker() {
        viewModel.startUpdateWorker(2,TimeUnit.MINUTES,this)
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

    private fun startNotification(){
        // 1
        val notificationManager = NotificationManagerCompat.from(this)
        val notificatin = NotificationCreator()
       val chanalId= notificatin.createNotificationChannelId(this,1,true,"MainActivity","ahlen")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

       val notify= notificatin.createNotification(this,"First","ahmed ashref",intent,chanalId)
        notificationManager.notify(1001,notify)

    }
}
