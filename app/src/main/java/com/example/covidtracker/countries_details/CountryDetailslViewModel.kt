package com.example.covidtracker.countries_details

import android.content.Context
import androidx.lifecycle.*
import com.example.covidtracker.core.INTERVAL_KEY
import com.example.covidtracker.core.PREFERENCE_KEY
import com.example.covidtracker.core.Repository
import com.example.covidtracker.core.local.shardPreference.SharedPreferenceBuilder
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.SubscripEntity
import com.example.covidtracker.utils.Helper
import com.example.covidtracker.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CountryDetailslViewModel  (val repository: Repository) : ViewModel(){

    // insert to subscrip table
    fun insertToSubscripTable(countryData: CountryData) {
        GlobalScope.async(Dispatchers.IO){
            repository.insertToSubscripTable(Helper.MapCountryDataToSubscribeCountryData(countryData))
        }
    }


    fun isSubscribed(countryData: CountryData)= liveData(Dispatchers.IO){
       emit(Resource.loading(data = null))
       try {
           emit(Resource.success(data = repository.isSubscribed(countryData.country)))
       } catch (exception: Exception) {
           emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
       }

        }
    fun unSubscribe(countryData: CountryData) {
        GlobalScope.async(Dispatchers.IO){
            repository.deleteSubscribedCountry(countryData.country)
        }
    }

    fun getCountryHistoricalData(countryName:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCountryHistoricalData(countryName)))
        }catch (exception:Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}