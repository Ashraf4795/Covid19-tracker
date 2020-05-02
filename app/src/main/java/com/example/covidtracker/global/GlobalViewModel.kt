package com.example.covidtracker.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.covidtracker.core.Repository
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.utils.Resource
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


// global fragment viewModel
class GlobalViewModel (val repository: Repository) :ViewModel(){

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


}