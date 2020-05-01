package com.example.covidtracker.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.covidtracker.core.Repository
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.utils.Resource
import kotlinx.coroutines.Dispatchers

// global fragment viewModel
class MainViewModel (val repository: Repository) :ViewModel(){

    fun getGlobalData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getGlobalDataFromNetwork()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    fun InsertGlobalDat(globalData: GlobalData) {
        liveData(Dispatchers.IO) {
        try {
            repository.insertGlobalToDataDase(globalData)
            emit(Resource.success(data =globalData))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }}

    fun getGlobalDataFromDb() =liveData(Dispatchers.IO) {
            try {
                emit(Resource.success(data =repository.getGlobalDataFromDataBase()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }


}