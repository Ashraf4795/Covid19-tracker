package com.example.covidtracker.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covidtracker.core.local.LocalDataBaseContract
import com.example.covidtracker.core.network.NetworkServiceContract
import com.example.covidtracker.main.MainViewModel

class ViewModelFactory(private val networkServiceContract: NetworkServiceContract,
                       private val localDataBaseContract: LocalDataBaseContract)
    : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Repository(networkServiceContract,localDataBaseContract)) as T
        }

        //check for other viewModels
        throw IllegalArgumentException("Unknown class name")
    }

}