package com.example.covidtracker.countries_details

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.bumptech.glide.Glide
import com.example.covidtracker.R
import com.example.covidtracker.core.COUNTRY_DATA_EXTRA_KEY
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.CountryHistorcalData
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.setting.Setting
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Status
import com.ramijemli.percentagechartview.callback.AdaptiveColorProvider
import com.ramijemli.percentagechartview.callback.ProgressTextFormatter
import kotlinx.android.synthetic.main.active_serious_layout.*
import kotlinx.android.synthetic.main.activity_countries_details.*
import kotlinx.android.synthetic.main.fragment_global.settingBtnId
import kotlinx.android.synthetic.main.fragment_global.todayBtnId
import kotlinx.android.synthetic.main.fragment_global.totalBtnId
import kotlinx.android.synthetic.main.population_details.*
import kotlinx.android.synthetic.main.statistics_card.*
import kotlinx.android.synthetic.main.total_card.*


class CountriesDetails : AppCompatActivity() {

    lateinit var countryData: CountryData
    private lateinit var viewModel: CountryDetailslViewModel
    var isSubscribed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_details)
        setUpCountryViewModel()
        if (intent.extras != null) {
            val bundle: Bundle = intent.extras!!
            countryData = bundle.get(COUNTRY_DATA_EXTRA_KEY) as CountryData
        }
        isCountrySubscribed(countryData)
        setUpPopulationData(countryData)
        setUpUI(countryData)
        getCountryHistoricalDataFromNetwork(countryData.country)

        todayBtnId.setOnClickListener {
            setUpTodayUI(countryData)
        }



        totalBtnId.setOnClickListener {
            setUpUI(countryData)
        }

        settingBtnId.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        //notify
        notifyBtnId.setOnClickListener {
            isSubscribed = !isSubscribed
            setNotifyButtonStyle()
            if(!isSubscribed)
            {
                viewModel.unSubscribe(countryData)
            }else
            {
                viewModel.insertToSubscripTable(countryData)
            }
        }

        backBtnId.setOnClickListener {
            finish()
        }
    }

    private fun isCountrySubscribed(countryData: CountryData) {
        viewModel.isSubscribed(countryData).observe(this, Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            if(it?.size!=0){
                                isSubscribed=true
                                setNotifyButtonStyle()
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }


    private fun setNotifyButtonStyle() {
        if (isSubscribed) {
            notifyBtnId.setBackgroundResource(R.drawable.un_notify_button)
            notifyBtnId.setText(R.string.unnotify)
            notifyBtnId.setTextColor(resources.getColor(R.color.textBlack))
        } else {
            notifyBtnId.setBackgroundResource(R.drawable.notify_button)
              notifyBtnId.setText(R.string.notifyMe)
            notifyBtnId.setTextColor(resources.getColor(R.color.white))
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
        ).get(CountryDetailslViewModel::class.java)
    }

    private fun setUpPopulationData(countryData: CountryData) {
        val result = ((countryData.recovered.toFloat() / countryData.cases.toFloat()) * 100)
        //percentageTVId.text = "$result%"
        percentage_chart_id.setProgress(result,true)
        percentage_chart_id.setTextFormatter(ProgressTextFormatter { progress ->  progress.toInt().toString()+"%"})
        
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

    private fun getCountryHistoricalDataFromNetwork(countryName:String){
        viewModel.getCountryHistoricalData(countryName).observe(this, Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            setCountryStatistics(it!!)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }


    private fun setCountryStatistics(countryHistorcalData: CountryHistorcalData) {
        val cartesian = AnyChart.line()
        cartesian.yAxis(false)
        cartesian.xAxis(true).labels(false)
        cartesian.xScroller(true)

        cartesian.animation(true)

        cartesian.crosshair()
            .yLabel(false) // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)



        val seriesData: MutableList<DataEntry> = ArrayList()
        countryHistorcalData.timeline.casesMap.forEach{
            seriesData.add(ValueDataEntry(it.key,it.value))
        }

        val seriesData2: MutableList<DataEntry> = ArrayList()
        countryHistorcalData.timeline.recoveredMap.forEach{
            seriesData2.add(ValueDataEntry(it.key,it.value))
        }

        val seriesData3: MutableList<DataEntry> = ArrayList()
        countryHistorcalData.timeline.deathsMap.forEach{
            seriesData3.add(ValueDataEntry(it.key,it.value))
        }

        val set= Set.instantiate()
        set.data(seriesData)
        val series1Mapping: Mapping = set.mapAs("{ value2: 'value2', x: 'x' }")

        val set2 = Set.instantiate()
        set2.data(seriesData2)
        val series2Mapping: Mapping = set2.mapAs("{ value2: 'value2', x: 'x' }")

        val set3 = Set.instantiate()
        set3.data(seriesData3)
        val series3Mapping: Mapping = set3.mapAs("{ value2: 'value2', x: 'x' }")

        val series1 = cartesian.line(series1Mapping)
        series1.hovered().markers().enabled(true)
        series1.name("confirmed")
        series1.stroke("#418BCA")
        series1.hovered().markers()
            .type(MarkerType.CROSS)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)


        val series2 = cartesian.line(series2Mapping)
        series2.name("Recovered")
        series2.hovered().markers().enabled(true)
        series2.stroke("#02B686")
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series3 = cartesian.line(series3Mapping)
        series3.stroke("#FF4947")
        series3.name("Death")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
            .stroke("blue")
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        any_chart_view.setChart(cartesian)
    }


    private enum class COLORS(val res:Int) {
        LOW(R.color.recoveryColor),
        MEDIUM(R.color.confirmedColor),
        HIGH(R.color.primeRed),
        DANGER(R.color.dangerous)
    }
}
