package com.example.covidtracker.global

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.covidtracker.core.Repository
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.utils.Resource
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.covidtracker.core.INTERVAL_KEY
import com.example.covidtracker.core.PREFERENCE_KEY
import com.example.covidtracker.core.local.shardPreference.SharedPreferenceBuilder
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.SubscripEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


// global fragment viewModel
class GlobalViewModel @Inject constructor(val repository: Repository) :ViewModel(){
    val globalMutableData:MutableLiveData<GlobalData> = MutableLiveData()

    fun getGlobalDataWithCountriesData()= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.refreshData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getGlobalDataFromNetwork() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getGlobalDataFromNetwork()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    fun insertGlobalData(globalData: GlobalData) {
        viewModelScope.launch {
            repository.insertGlobalToDataDase(globalData)
        }
    }

    fun getGlobalDataFromDatabase() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getGlobalDataFromDataBase()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun deleteGlobalDataFromDatabase() {
        viewModelScope.launch {
            repository.deleteGlobalFromDataBase()
        }
    }

    fun getGlobalHistoricalData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getGlobalHistoricalData()))
        }catch (exception:Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getCountryHistoricalData(countryName : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCountryHistoricalData(countryName)))
        }catch (exception:Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    //start worker from sharedPrefernce
    fun startUpdateWorker (interval:Long,timeUnit: TimeUnit,context: Context) {
        SharedPreferenceBuilder(context, PREFERENCE_KEY).saveIntervalAndUpdateWorkerRequest(INTERVAL_KEY,interval,timeUnit)
    }

    //update interval data
    fun updateIntervalValue (interval: Long,timeUnit: TimeUnit,context: Context){
        SharedPreferenceBuilder(context, PREFERENCE_KEY).saveIntervalAndUpdateWorkerRequest(INTERVAL_KEY,interval,timeUnit)
    }

    // insert to subscrip table
    fun insertToSubscripTable(subscripEntity: SubscripEntity) {
        GlobalScope.async(Dispatchers.IO){
            repository.insertToSubscripTable(subscripEntity)
        }
    }

    //get countriesData from Network
    fun getCountriesDataFromNetwork() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCountriesDataFromNetwork()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    //get countriesData from DataBase
    fun getCountriesDataFromDatabase() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCountriesDataFromDataBase()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }



}