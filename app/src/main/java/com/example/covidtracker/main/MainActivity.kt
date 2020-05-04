package com.example.covidtracker.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.covidtracker.R
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.global.GlobalViewModel
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.active_serious_layout.*
import kotlinx.android.synthetic.main.fragment_global.*
import kotlinx.android.synthetic.main.total_card.*

class MainActivity : AppCompatActivity() {
    //todo: setup workmanager
    //todo: save to database
    //todo:check connectivity, if offline load from db if exist
    //todo:setup recyclerview
    //todo:setup statistics chart
    
    private lateinit var viewModel: GlobalViewModel
    private lateinit var globalData: GlobalData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_global)
        setUpGlobalViewModel()
        getData()
        todayBtnId.setOnClickListener{
            setUpTodayUI(globalData)
        }
        totalBtnId.setOnClickListener{
            setUpUI(globalData)
        }
    }

    private fun setUpUI(globalData: GlobalData) {
        recoverLayoutId.isVisible = true
        confirmTextViewId.text = Helper.convertNumber(globalData.cases)
        recoveredTextViewId.text = Helper.convertNumber(globalData.recovered)
        deathsTextViewId.text = Helper.convertNumber(globalData.deaths)
        activeTextViewId.text = Helper.convertNumber(globalData.active)
        seriousTextViewId.text = Helper.convertNumber(globalData.critical)
    }

    private fun setUpTodayUI(globalData: GlobalData) {
        recoverLayoutId.isVisible = false
        confirmTextViewId.text = Helper.convertNumber(globalData.todayCases)
        deathsTextViewId.text = Helper.convertNumber(globalData.todayDeaths)

    }

    private fun getData() {
        viewModel.getGlobalDataFromNetwork().observe(this,Observer{
            it.let {
                when(it.status){
                    Status.SUCCESS ->{
                        it?.data.let {
                            if (it != null) {
                                setUpUI(it)
                                globalData = it
                                progressBarId.visibility = View.GONE
                            }
                        }
                    }
                    Status.ERROR ->{
                        Toast.makeText(this@MainActivity,"Connection Issue",Toast.LENGTH_LONG)
                    }
                    Status.LOADING ->{
                        progressBarId.visibility = View.VISIBLE
                    }
                }
            }
        })
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
