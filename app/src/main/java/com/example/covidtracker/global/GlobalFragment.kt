package com.example.covidtracker.global

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.covidtracker.R
import com.example.covidtracker.core.Refreshable
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.GlobalHistoricalData
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.setting.Setting
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.active_serious_layout.*
import kotlinx.android.synthetic.main.fragment_global.*
import kotlinx.android.synthetic.main.statistics_card.*
import kotlinx.android.synthetic.main.total_card.*

class GlobalFragment : Fragment(), Refreshable {

    private lateinit var viewModel: GlobalViewModel
    private lateinit var globalData: GlobalData

    //TODO:1- get countries data ------done
    //TODO:2- initialize adapter and pass countires list to it -----done
    //TODO:3- save countries list to DB ----no
    //TODO:4 notifiy recyclerView Data changed ---done

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
        getCountriesData()
        getData()
        getStatisticsData()
        // val d:Cartesian = AnyChart.line()

        todayBtnId.setOnClickListener {
            setUpTodayUI(globalData)
        }
        totalBtnId.setOnClickListener {
            setUpUI(globalData)
        }

        settingBtnId.setOnClickListener {
            val intent = Intent(requireContext(), Setting::class.java)
            startActivity(intent)
        }

        swipeRefreshLayoutGlobal.setOnRefreshListener {
            refresh()
        }

        swipeRefreshLayoutGlobal.setColorSchemeResources(
            R.color.primeRed,
            R.color.confirmedColor,
            R.color.recoveryColor
        )


    }

    override fun refresh() {
        viewModel.getGlobalDataWithCountriesData().observe(viewLifecycleOwner, Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            if (it != null) {
                                setUpUI(it.first)
                                globalData = it.first
                                swipeRefreshLayoutGlobal.isRefreshing = false
                            }
                        }
                    }
                    Status.ERROR -> {
                        swipeRefreshLayoutGlobal.isRefreshing = false
                        Toast.makeText(requireContext(), "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                        swipeRefreshLayoutGlobal.isRefreshing = true
                    }
                }
            }
        })
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
        viewModel.getGlobalDataFromDatabase().observe(viewLifecycleOwner, Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            if (it != null) {
                                setUpUI(it)
                                globalData = it
                                progressBarId.visibility = View.GONE
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                        progressBarId.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getStatisticsData(){
        viewModel.getGlobalHistoricalData().observe(requireActivity(), Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            if (it != null) {
                                setGlobalStatistics(it)
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
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


    //get CountreisData
    private fun getCountriesData() {
        viewModel.getCountriesDataFromNetwork().observe(viewLifecycleOwner, Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            setRecyclerData(it as ArrayList<CountryData>)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                        progressBarId.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    fun setRecyclerData(Countries: ArrayList<CountryData>) {
        val data = Countries.sortedByDescending { it.cases }.take(10)
        topCountriesRecyclerViewId.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        topCountriesRecyclerViewId.adapter =
            TopCountriesAdapterAdapter(data as ArrayList<CountryData>, requireContext())

    }

    private fun setGlobalStatistics(globalHistoricalData: GlobalHistoricalData) {
        val cartesian = AnyChart.line()
        cartesian.yAxis(false)
        cartesian.xAxis(true).labels(false)
        cartesian.xScroller(true)

        cartesian.animation(true)
        cartesian.title("Numbers in Million")

        cartesian.crosshair()
            .yLabel(false) // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)



        val seriesData: MutableList<DataEntry> = ArrayList()
        globalHistoricalData.casesMap.forEach{
            seriesData.add(ValueDataEntry(it.key,it.value))
        }

        val seriesData2: MutableList<DataEntry> = ArrayList()
        globalHistoricalData.recoveredMap.forEach{
            seriesData2.add(ValueDataEntry(it.key,it.value))
        }

        val seriesData3: MutableList<DataEntry> = ArrayList()
        globalHistoricalData.deathsMap.forEach{
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
        series1.name("Death")
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


}

