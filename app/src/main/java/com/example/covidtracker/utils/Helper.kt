package com.example.covidtracker.utils

import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.SubscripEntity
import java.lang.StringBuilder

class Helper {


    companion object {
        fun convertNumber(number:Long) :String {
            val stringBuilder = StringBuilder()
            when(number) {
                in 1000..999999 ->{
                    stringBuilder.append(String.format("%.1f",(number/1000f)))
                    stringBuilder.append("K")
                    return stringBuilder.toString()
                }
                in 1000000..999999999 ->{
                    stringBuilder.append(String.format("%.1f",(number/1000000f)))
                    stringBuilder.append("M")
                    return stringBuilder.toString()
                }
            }
            return number.toString()
        }

        fun MapCountryDataToSubscribeCountryData(countryData: CountryData):SubscripEntity
        {
           return SubscripEntity(countryData.active,
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
    }
}