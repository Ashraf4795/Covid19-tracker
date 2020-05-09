package com.example.covidtracker.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covidtracker.core.local.LocalDataBaseContract
import com.example.covidtracker.core.network.NetworkServiceContract
import com.example.covidtracker.countries.CountryViewModel
import com.example.covidtracker.countries_details.CountryDetailslViewModel
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
        else if(modelClass.isAssignableFrom(CountryViewModel::class.java)){
                return CountryViewModel(
                    Repository(
                        networkServiceContract,
                        localDataBaseContract
                    )
                ) as T

        }
        else if(modelClass.isAssignableFrom(CountryDetailslViewModel::class.java)){
            return CountryDetailslViewModel(
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