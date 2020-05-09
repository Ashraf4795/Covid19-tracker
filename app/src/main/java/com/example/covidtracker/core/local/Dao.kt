package com.example.covidtracker.core.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.SubscripEntity
import retrofit2.http.Path

@Dao
interface Dao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobal(globalData: GlobalData)

    @Query("Select * from globalData")
    suspend fun getGloabal():GlobalData

    @Query("delete from globalData")
    suspend fun deleteGlobal()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countriesData: List<CountryData>)

    @Query("Select * from country")
    suspend fun getCountries():List<CountryData>

    @Query("delete from country")
    suspend fun deleteCountries()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToSubscripTable(subscripEntity:SubscripEntity)

    //get subscriped country
    @Query("SELECT * FROM subscribTable")
    suspend fun getSubscripedCountry():List<SubscripEntity>

    //check if country is Subscribed
    @Query("SELECT * FROM subscribTable WHERE country LIKE :countryName")
    suspend fun isSubscribed(countryName:String):List<SubscripEntity>

    //delete subscribe Country
    @Query("DELETE FROM subscribTable WHERE country LIKe :countryName")
    suspend fun deleteSubscribeCountry(countryName: String)
}