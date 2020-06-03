package com.example.covidtracker.core.network

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.CountryHistorcalData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.GlobalHistoricalData
import javax.inject.Inject

interface NetworkServiceContract{


    suspend fun getGlobalData() : GlobalData

    suspend fun  getCountriesData() : List<CountryData>

    suspend fun getCountryData(countryName:String) : CountryData

    suspend fun getGlobalHistoricalData():GlobalHistoricalData

    suspend fun getCountryHistoricalData(countryName:String):CountryHistorcalData

}