package com.example.covidtracker.core.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "country")
data class CountryData(
    val active: Long,
    val cases: Long,
    val casesPerOneMillion: Long,
    val continent: String,
    val country: String,
    @Embedded(prefix = "countryInfo")
    val countryInfo: CountryInfo,
    val critical: Long,
    val deaths: Long,
    val deathsPerOneMillion: Long,
    val recovered: Long,
    val tests: Long,
    val testsPerOneMillion: Long,
    val todayCases: Long,
    val todayDeaths: Long,
    @PrimaryKey
    val updated: Long
)
