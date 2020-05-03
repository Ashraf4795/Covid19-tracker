package com.example.covidtracker.core

import android.util.Log
import com.example.covidtracker.core.local.LocalDataBaseContract
import com.example.covidtracker.core.models.*
import com.example.covidtracker.core.network.NetworkServiceContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class Repository(val networkContract : NetworkServiceContract,val localContract:LocalDataBaseContract){

    // Data From Network
    //get Global data
    suspend fun getGlobalDataFromNetwork() = networkContract.getGlobalData()

    //get Countries data
    suspend fun  getCountriesDataFromNetwork() = networkContract.getCountriesData()

    //get country data with name
    suspend fun getCountryDataFromNetwork(countryName:String) = networkContract.getCountryData(countryName)

    //get global historical data
    suspend fun  getGlobalHistoricalData():GlobalHistoricalData =
        networkContract.getGlobalHistoricalData()

    //get Country Historcal Data
    suspend fun  getCountryHistoricalData(countryName: String):CountryHistorcalData =
        networkContract.getCountryHistoricalData(countryName)



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

    //insert country to subscrip table
    suspend fun insertToSubscripTable(subscripEntity: SubscripEntity){
        localContract.insertToSubscripTable(subscripEntity)
    }

    // get all subscriped countries
    suspend fun getSubscripedCountries() = localContract.getSubscriptedCountry()

    //refresh data function
    suspend fun refreshData () {
        val countriesData = GlobalScope.async(Dispatchers.IO) {
            getCountriesDataFromNetwork()
        }.await()

        val globalData = GlobalScope.async(Dispatchers.IO) {
            getGlobalDataFromNetwork()
        }.await()

        if (countriesData.count()>0) {
            Log.d("refresh","insert countries :: " + countriesData.toString())
            insertCountiesToDataBase(countriesData)
        }
        Log.d("refresh","insert GlobapData :: " + globalData.toString())
        insertGlobalToDataDase(globalData)


    }


}