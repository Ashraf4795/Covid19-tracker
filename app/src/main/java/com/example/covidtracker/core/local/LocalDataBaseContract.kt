package com.example.covidtracker.core.local

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.SubscripEntity

interface LocalDataBaseContract{

    suspend fun insertGlobal(globalData: GlobalData)
    suspend fun getGloabal():GlobalData
    suspend fun deleteGlobal()
    suspend fun insertCountry(countriesData: List<CountryData>)
    suspend fun getCountries():List<CountryData>
    suspend fun deleteCountries()
    suspend fun insertToSubscripTable(subscripEntity: SubscripEntity)

    //test
    suspend fun getSubscriptedCountry():List<SubscripEntity>
}