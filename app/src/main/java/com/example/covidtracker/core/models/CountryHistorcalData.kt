package com.example.covidtracker.core.models

data class CountryHistorcalData(
    val country: String,
    val provinces: List<String>,
    val timeline: GlobalHistoricalData
)