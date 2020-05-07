package com.example.covidtracker.countries

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Status
import kotlinx.android.synthetic.main.countries_fragment.*
import kotlinx.android.synthetic.main.total_card.*


class CountryFragment : Fragment() {
    private lateinit var viewModel: CountryViewModel
    private lateinit var countriesData: List<CountryData>



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
            val africiList=countriesData.filter{data ->data.continent=="Africa"}
            setCountriesRecyclerData(africiList as ArrayList<CountryData>)
        }
        asiaChipBtn.setOnClickListener {
            val asiaList=countriesData.filter{data ->data.continent=="Asia"}
            setCountriesRecyclerData(asiaList as ArrayList<CountryData>)
        }
        europeChipBtn.setOnClickListener {
            val europeList=countriesData.filter{data ->data.continent=="Europe"}
            setCountriesRecyclerData(europeList as ArrayList<CountryData>)
        }
        northAmaricaChipBtn.setOnClickListener {
            val northAmaricaList=countriesData.filter{data ->data.continent=="North America"}
            setCountriesRecyclerData(northAmaricaList as ArrayList<CountryData>)
        }
        southAmaricaChipBtn.setOnClickListener {
            val southAmaricaList=countriesData.filter{data ->data.continent=="South America"}
            setCountriesRecyclerData(southAmaricaList as ArrayList<CountryData>)
        }
        australiaChipBtn.setOnClickListener {
            val australiaList=countriesData.filter{data ->data.continent=="Australia/Oceania"}
            setCountriesRecyclerData(australiaList as ArrayList<CountryData>)
        }


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
        viewModel.getGlobalDataFromNetwork().observe(viewLifecycleOwner, Observer{
            it.let {
                when(it.status){
                    Status.SUCCESS ->{
                        it?.data.let {
                            if (it != null) {
                                setUpGlobalCard(it)
                            }
                        }
                    }
                    Status.ERROR ->{
                        Toast.makeText(requireContext(),"Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING ->{
                    }
                }
            }
        })
    }

    //get CountreisData
    private fun getCountriesData() {
        viewModel.getCountriesDataFromNetwork().observe(viewLifecycleOwner, Observer{
            it.let {
                when(it.status){
                    Status.SUCCESS ->{
                        it?.data.let {
                            if (it != null) {
                                countriesData= it
                            }
                         setCountriesRecyclerData(it as ArrayList<CountryData>)
                        }
                    }
                    Status.ERROR ->{
                        Toast.makeText(requireContext(),"Connection Issue", Toast.LENGTH_LONG)
                    }
                    Status.LOADING ->{
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
    fun setCountriesRecyclerData(Countries:ArrayList<CountryData>){
        countriesRecyclerViewId.layoutManager=  LinearLayoutManager(requireContext())
        countriesRecyclerViewId.adapter=
            CountriesAdapter(Countries,requireContext())
    }
}
