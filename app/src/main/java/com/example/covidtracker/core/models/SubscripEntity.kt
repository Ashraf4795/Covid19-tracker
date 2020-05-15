package com.example.covidtracker.core.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

// entity to descripe a subscriped country
@Entity(tableName = "subscribTable")
data class SubscripEntity(
    val active: Long,
    val cases: Long,
    val casesPerOneMillion: Double,
    val continent: String,
    val country: String,
    @Embedded(prefix = "countryInfo")
    val countryInfo: CountryInfo,
    val critical: Long,
    val deaths: Long,
    val deathsPerOneMillion: Double,
    val recovered: Long,
    val tests: Long,
    val testsPerOneMillion: Double,
    val todayCases: Long,
    val todayDeaths: Long,
    @PrimaryKey
    val updated: Long
)
