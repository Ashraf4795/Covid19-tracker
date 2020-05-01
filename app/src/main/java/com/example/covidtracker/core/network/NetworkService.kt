package com.example.covidtracker.core.network

import com.example.covidtracker.core.models.CountryData

class NetworkService (val networkServiceContract: NetworkServiceContract) : NetworkServiceContract{

    override suspend fun getGlobalData()  = networkServiceContract.getGlobalData()

    override suspend fun getCountriesData(): List<CountryData> = networkServiceContract.getCountriesData()

    override suspend fun getCountryData(countryName:String): CountryData =
        networkServiceContract.getCountryData(countryName)

}