package com.example.covidtracker.core.local

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData

interface LocalDataBaseContract{

    suspend fun insertGlobal(globalData: GlobalData)
    suspend fun getGloabal():GlobalData
    suspend fun deleteGlobal()
    suspend fun insertCountry(countriesData: List<CountryData>)
    suspend fun getCountries():List<CountryData>
    suspend fun deleteCountries()
}