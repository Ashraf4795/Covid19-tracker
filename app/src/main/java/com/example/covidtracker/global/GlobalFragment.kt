package com.example.covidtracker.global

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.active_serious_layout.*
import kotlinx.android.synthetic.main.fragment_global.*
import kotlinx.android.synthetic.main.total_card.*


class GlobalFragment : Fragment() {

    private lateinit var viewModel: GlobalViewModel
    private lateinit var globalData: GlobalData

    //TODO:1- get countries data
    //TODO:2- initialize adapter and pass countires list to it
    //TODO:3- save countries list to DB
    //TODO:4 notifiy recyclerView Data changed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        viewModel.getGlobalDataFromNetwork().observe(viewLifecycleOwner, Observer{
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
                        Toast.makeText(requireContext(),"Connection Issue", Toast.LENGTH_LONG)
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
                LocalDataBase(DatabaseBuilder.getInstance(requireContext()))
            )
        ).get(GlobalViewModel::class.java)
    }
}
