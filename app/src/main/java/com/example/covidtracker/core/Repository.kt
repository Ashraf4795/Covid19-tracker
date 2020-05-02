package com.example.covidtracker.core

import com.example.covidtracker.core.local.LocalDataBaseContract
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.CountryHistorcalData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.GlobalHistoricalData
import com.example.covidtracker.core.network.NetworkServiceContract



class Repository(val networkContract : NetworkServiceContract,val localContract:LocalDataBaseContract){

    // Data From Network
    //get Global data
    suspend fun getGlobalDataFromNetwork() = networkContract.getGlobalData()

    //get Countries data
    suspend fun  getCountriesDataFromNetwork() = networkContract.getCountriesData()

    //get country data with name
    suspend fun getCountryDataFromNetwork(countryName:String) = networkContract.getCountryData(countryName)

    // Data From LocalDataBase

    //insert all Data into Database
    suspend fun insertGlobalToDataDase(globalData: GlobalData) {
        localContract.insertGlobal(globalData)
    }
    // get All Data From Database
    suspend fun getGlobalDataFromDataBase(): GlobalData = localContract.getGloabal()

    //Delete All data from Database
    suspend fun deleteGlobalFromDataBase() {
        localContract.deleteGlobal()
    }

    //insert all countries into Database
    suspend fun insertCountiesToDataBase(countriesData: List<CountryData>) {
        localContract.insertCountry(countriesData)
    }

    //get all countries from Database
    suspend fun getCountriesDataFromDataBase():List<CountryData> = localContract.getCountries()

    // delete all countreis form database
    suspend fun deleteCountriesFromDataBase() {
        localContract.deleteCountries()
    }

    //get global historical data
    suspend fun  getGlobalHistoricalData():GlobalHistoricalData =
        networkContract.getGlobalHistoricalData()

    //get Country Historcal Data
    suspend fun  getCountryHistoricalData(countryName: String):CountryHistorcalData =
        networkContract.getCountryHistoricalData(countryName)



}