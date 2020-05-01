package com.example.covidtracker.core.network

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.GlobalHistoricalData

interface NetworkServiceContract{


    suspend fun getGlobalData() : GlobalData

    suspend fun  getCountriesData() : List<CountryData>

    suspend fun getCountryData(countryName:String) : CountryData

    suspend fun getGlobalHistoricalData():GlobalHistoricalData
}