package com.example.covidtracker.core.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData

@Dao
interface Dao{

    @Insert
    suspend fun insertGlobal(globalData: GlobalData)

    @Query("Select * from globalData")
    suspend fun getGloabal():GlobalData

    @Query("delete from globalData")
    suspend fun deleteGlobal()

    @Insert
    suspend fun insertCountry(countriesData: List<CountryData>)

    @Query("Select * from country")
    suspend fun getCountries():List<CountryData>

    @Query("delete from country")
    suspend fun deleteCountries()

}