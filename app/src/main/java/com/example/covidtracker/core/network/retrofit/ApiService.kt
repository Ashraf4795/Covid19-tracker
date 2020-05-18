package com.example.covidtracker.core.network.retrofit

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.CountryHistorcalData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.GlobalHistoricalData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    //get global data
    @GET("/v2/all")
    suspend fun getGlobalData () : GlobalData

    //get all countries data
    @GET("/v2/Countries")
    suspend fun getCountriesData () : List<CountryData>

    //get country data with name
    @GET("/v2/Countries/{country}")
    suspend fun getCountryData (
        @Path("country") countryName:String
    ) : CountryData
    
    //get global history
    @GET("/v2/historical/all?lastdays=0")
    suspend fun getGlobalHistoricalData ():GlobalHistoricalData

    //get Country history
    @GET("v2/historical/{country}?lastdays=0")
    suspend fun getCountryHistoricalData (
        @Path("country") countryName:String
    ):CountryHistorcalData

}