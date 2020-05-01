package com.example.covidtracker.core.network.retrofit

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import retrofit2.http.GET
import retrofit2.http.Path

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


}