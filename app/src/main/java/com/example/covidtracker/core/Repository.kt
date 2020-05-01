package com.example.covidtracker.core

import com.example.covidtracker.core.network.NetworkServiceContract

class Repository (val networkContract : NetworkServiceContract){

    //get Global data
    suspend fun getGlobalData () = networkContract.getGlobalData()

    //get Countries data
    suspend fun  getCountriesData () = networkContract.getCountriesData()

    //get country data with name
    suspend fun getCountryData(countryName:String) = networkContract.getCountryData(countryName)

}