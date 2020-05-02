package com.example.covidtracker.core.network

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.CountryHistorcalData
import com.example.covidtracker.core.models.GlobalHistoricalData

class NetworkService (val networkServiceContract: NetworkServiceContract) : NetworkServiceContract{

    override suspend fun getGlobalData()  = networkServiceContract.getGlobalData()

    override suspend fun getCountriesData(): List<CountryData> = networkServiceContract.getCountriesData()

    override suspend fun getCountryData(countryName:String): CountryData =
        networkServiceContract.getCountryData(countryName)

    override suspend fun getGlobalHistoricalData(): GlobalHistoricalData  =
        networkServiceContract.getGlobalHistoricalData()

    override suspend fun getCountryHistoricalData(countryName:String): CountryHistorcalData =
        networkServiceContract.getCountryHistoricalData(countryName)

}