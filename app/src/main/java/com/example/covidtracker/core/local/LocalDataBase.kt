package com.example.covidtracker.core.local

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData

class LocalDataBase(private val appDataBase: AppDataBase):LocalDataBaseContract{

    override suspend fun insertGlobal(globalData: GlobalData) {
        appDataBase.getDao().insertGlobal(globalData)
    }

    override suspend fun getGloabal(): GlobalData {

        return appDataBase.getDao().getGloabal()
    }

    override suspend fun deleteGlobal() {
          appDataBase.getDao().deleteGlobal()
    }

    override suspend fun insertCountry(countriesData: List<CountryData>) {
          appDataBase.getDao().insertCountry(countriesData)

    }

    override suspend fun getCountries(): List<CountryData> {
        return appDataBase.getDao().getCountries()
    }

    override suspend fun deleteCountries() {
         appDataBase.getDao().deleteCountries()
    }
}