package com.example.covidtracker.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.SubscripEntity
import java.lang.StringBuilder

class Helper {


    companion object {
        fun convertNumber(number: Long): String {
            val stringBuilder = StringBuilder()
            when (number) {
                in 1000..999999 -> {
                    stringBuilder.append(String.format("%.1f", (number / 1000f)))
                    stringBuilder.append("K")
                    return stringBuilder.toString()
                }
                in 1000000..999999999 -> {
                    stringBuilder.append(String.format("%.1f", (number / 1000000f)))
                    stringBuilder.append("M")
                    return stringBuilder.toString()
                }
            }
            return number.toString()
        }

        fun compareFetchedDataWithLocalData(
            countriesData: List<CountryData>,
            subscribedCountries: List<SubscripEntity>
        ): List<SubscripEntity> {
            val notifiedCountries = ArrayList<SubscripEntity>()
            subscribedCountries.forEach { subscribe ->
                countriesData.forEach {countryData ->
                    if (countryData.countryInfo._id == subscribe.countryInfo._id) {
                        if (countryData.updated != subscribe.updated) {
                            notifiedCountries.add(Helper.MapCountryDataToSubscribeCountryData(countryData))
                        }
                    }
                }
            }
            return notifiedCountries
        }


        fun MapCountryDataToSubscribeCountryData(countryData: CountryData): SubscripEntity {
            return SubscripEntity(
                countryData.active,
                countryData.cases,
                countryData.casesPerOneMillion,
                countryData.continent,
                countryData.country,
                countryData.countryInfo,
                countryData.critical,
                countryData.deaths,
                countryData.deathsPerOneMillion,
                countryData.recovered,
                countryData.tests,
                countryData.testsPerOneMillion,
                countryData.todayCases,
                countryData.todayDeaths,
                countryData.updated
            )
        }

        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }

        fun decreaseDigits(num:Long):Long{
            return num/1000000
        }
    }


}