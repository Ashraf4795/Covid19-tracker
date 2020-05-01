package com.example.covidtracker.core.models

data class CountryData(
    val active: Long,
    val cases: Long,
    val casesPerOneMillion: Long,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Long,
    val deaths: Long,
    val deathsPerOneMillion: Long,
    val recovered: Long,
    val tests: Long,
    val testsPerOneMillion: Long,
    val todayCases: Long,
    val todayDeaths: Long,
    val updated: Long
)