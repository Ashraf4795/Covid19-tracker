package com.example.covidtracker.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covidtracker.core.local.LocalDataBaseContract
import com.example.covidtracker.core.network.NetworkServiceContract
import com.example.covidtracker.global.GlobalViewModel

class ViewModelFactory(private val networkServiceContract: NetworkServiceContract,
                       private val localDataBaseContract: LocalDataBaseContract)
    : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GlobalViewModel::class.java)) {
            return GlobalViewModel(
                Repository(
                    networkServiceContract,
                    localDataBaseContract
                )
            ) as T
        }

        //check for other viewModels
        throw IllegalArgumentException("Unknown class name")
    }

}