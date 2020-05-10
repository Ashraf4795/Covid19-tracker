package com.example.covidtracker.countries

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidtracker.R
import com.example.covidtracker.core.ViewModelFactory
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.setting.Setting
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.countries_fragment.*
import kotlinx.android.synthetic.main.total_card.*
import java.util.*
import kotlin.collections.ArrayList


class CountryFragment : Fragment() {
    private lateinit var viewModel: CountryViewModel
    private var countriesData: List<CountryData> = ArrayList<CountryData>()
    private lateinit var adapter: CountriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.countries_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpCountryViewModel()
        getData()
        getCountriesData()

        allChipBtn.setOnClickListener {
            setCountriesRecyclerData(countriesData as ArrayList<CountryData>)
        }
        africaChipBtn.setOnClickListener {
            val africiList = countriesData.filter { data -> data.continent == "Africa" }
            setCountriesRecyclerData(africiList as ArrayList<CountryData>)
        }
        asiaChipBtn.setOnClickListener {
            val asiaList = countriesData.filter { data -> data.continent == "Asia" }
            setCountriesRecyclerData(asiaList as ArrayList<CountryData>)
        }
        europeChipBtn.setOnClickListener {
            val europeList = countriesData.filter { data -> data.continent == "Europe" }
            setCountriesRecyclerData(europeList as ArrayList<CountryData>)
        }
        northAmaricaChipBtn.setOnClickListener {
            val northAmaricaList =
                countriesData.filter { data -> data.continent == "North America" }
            setCountriesRecyclerData(northAmaricaList as ArrayList<CountryData>)
        }
        southAmaricaChipBtn.setOnClickListener {
            val southAmaricaList =
                countriesData.filter { data -> data.continent == "South America" }
            setCountriesRecyclerData(southAmaricaList as ArrayList<CountryData>)
        }
        australiaChipBtn.setOnClickListener {
            val australiaList =
                countriesData.filter { data -> data.continent == "Australia/Oceania" }
            setCountriesRecyclerData(australiaList as ArrayList<CountryData>)
        }

        settingBtnId2.setOnClickListener {
            val intent = Intent(requireContext(), Setting::class.java)
            startActivity(intent)
        }

        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString());

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData().observe(viewLifecycleOwner, Observer {
                it.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            it?.data.let {
                                if (it != null) {
                                    setUpGlobalCard(it.first)
                                    setCountriesRecyclerData(it.second as ArrayList)
                                }
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), "Connection Issue", Toast.LENGTH_LONG)
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })
            swipeRefreshLayout.isRefreshing = false
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

    }


    //filter data
    fun filter(text: String): Unit {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<CountryData> = ArrayList()
        Log.d("fffff", "" + countriesData.size)
        //looping through existing elements
        for (s in countriesData) {
            //if the existing elements contains the search input
            if (s.country.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                //adding the element to filtered list
                filterdNames.add(s)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        Log.d("ddddd", "" + filterdNames.size)
        adapter?.filterList(filterdNames)
    }


    //set Up view Model
    private fun setUpCountryViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                RetrofitApiHelper(RetrofitBuilder.apiService),
                LocalDataBase(DatabaseBuilder.getInstance(requireContext()))
            )
        ).get(CountryViewModel::class.java)
    }

    //get World Cases and Recoverd and Dethes
    private fun getData() {
        viewModel.getGlobalDataFromDatabase().observe(viewLifecycleOwner, Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            if (it != null) {
                                setUpGlobalCard(it)
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    //get CountreisData
    private fun getCountriesData() {
        viewModel.getCountriesDataFromDatabase().observe(viewLifecycleOwner, Observer {
            it.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it?.data.let {
                            if (it != null) {
                                countriesData = it
                            }
                            setCountriesRecyclerData(it as ArrayList<CountryData>)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    //Data for World
    private fun setUpGlobalCard(globalData: GlobalData) {
        confirmTextViewId.text = Helper.convertNumber(globalData.cases)
        recoveredTextViewId.text = Helper.convertNumber(globalData.recovered)
        deathsTextViewId.text = Helper.convertNumber(globalData.deaths)
    }


    //setUp country RecyclerView
    fun setCountriesRecyclerData(Countries: ArrayList<CountryData>) {
        countriesRecyclerViewId.layoutManager = LinearLayoutManager(requireContext())
        adapter = CountriesAdapter(Countries, requireContext())
        countriesRecyclerViewId.adapter = adapter
    }
}
