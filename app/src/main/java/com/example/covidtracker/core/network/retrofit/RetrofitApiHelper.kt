package com.example.covidtracker.core.network.retrofit

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalHistoricalData
import com.example.covidtracker.core.network.NetworkServiceContract

class RetrofitApiHelper (val apiService: ApiService) : NetworkServiceContract {

    override suspend fun getGlobalData() = apiService.getGlobalData()

    override suspend fun  getCountriesData() : List<CountryData> = apiService.getCountriesData()

    override suspend fun getCountryData(countryName :String) : CountryData = apiService.getCountryData(countryName)

    override suspend fun getGlobalHistoricalData(): GlobalHistoricalData = apiService.getGlobalHistoricalData()



}