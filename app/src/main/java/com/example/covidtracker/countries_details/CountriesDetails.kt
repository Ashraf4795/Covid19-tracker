package com.example.covidtracker.countries_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.covidtracker.R
import com.example.covidtracker.core.COUNTRY_DATA_EXTRA_KEY
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.countries.CountryViewModel
import com.example.covidtracker.global.GlobalViewModel
import com.example.covidtracker.setting.Setting
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.active_serious_layout.*
import kotlinx.android.synthetic.main.activity_countries_details.*
import kotlinx.android.synthetic.main.country_item.*
import kotlinx.android.synthetic.main.fragment_global.*
import kotlinx.android.synthetic.main.fragment_global.settingBtnId
import kotlinx.android.synthetic.main.fragment_global.todayBtnId
import kotlinx.android.synthetic.main.fragment_global.totalBtnId
import kotlinx.android.synthetic.main.population_details.*
import kotlinx.android.synthetic.main.total_card.*
import kotlinx.android.synthetic.main.total_card.confirmTextViewId
import kotlinx.android.synthetic.main.total_card.recoveredTextViewId

class CountriesDetails : AppCompatActivity() {

    lateinit var  countryData:CountryData
    private lateinit var viewModel: GlobalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_details)
        setUpCountryViewModel()

        if(intent.extras !=null){
            val bundle:Bundle = intent.extras!!
            countryData = bundle.get(COUNTRY_DATA_EXTRA_KEY) as CountryData
        }

        setUpPopulationData(countryData)
        setUpUI(countryData)
        todayBtnId.setOnClickListener{
            setUpTodayUI(countryData)
        }

        totalBtnId.setOnClickListener{
            setUpUI(countryData)
        }

        settingBtnId.setOnClickListener{
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        //notify
        notifyBtnId.setOnClickListener{
        }

        backBtnId.setOnClickListener{
            finish()
        }
    }


    //set Up view Model
    private fun setUpCountryViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                RetrofitApiHelper(RetrofitBuilder.apiService),
                LocalDataBase(DatabaseBuilder.getInstance(this))
            )
        ).get(GlobalViewModel::class.java)
    }

    private fun setUpPopulationData(countryData: CountryData) {
        val result = ((countryData.recovered.toFloat()/countryData.cases.toFloat())*100).toInt()
        percentageTVId.text = "$result%"

        Log.d("abc", "${countryData.recovered}")

        Log.d("abc", "${countryData.cases}")

        casesPerMillionTVId.text = countryData.casesPerOneMillion.toString()
        testPerMillionTVId.text = countryData.testsPerOneMillion.toString()
    }


    private fun setUpUI(countryData: CountryData) {
        recoverLayoutId.isVisible = true
        confirmTextViewId.text = Helper.convertNumber(countryData.cases)
        recoveredTextViewId.text = Helper.convertNumber(countryData.recovered)
        deathsTextViewId.text = Helper.convertNumber(countryData.deaths)
        activeTextViewId.text = Helper.convertNumber(countryData.active)
        seriousTextViewId.text = Helper.convertNumber(countryData.critical)
        countryNameTV.text = countryData.country
        Glide.with(this).load(countryData.countryInfo.flag).into(countryFlag)
    }

    private fun setUpTodayUI(countryData: CountryData) {
        recoverLayoutId.isVisible = false
        confirmTextViewId.text = Helper.convertNumber(countryData.todayCases)
        deathsTextViewId.text = Helper.convertNumber(countryData.todayDeaths)
    }


}
