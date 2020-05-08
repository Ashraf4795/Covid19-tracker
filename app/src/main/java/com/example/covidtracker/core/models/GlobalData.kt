package com.example.covidtracker.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "globalData")
data class GlobalData(
    val active: Long,
    val affectedCountries: Int,
    val cases: Long,
    val casesPerOneMillion: Int,
    val critical: Long,
    val deaths: Long,
    val deathsPerOneMillion: Int,
    val recovered: Long,
    val tests: Long,
    val testsPerOneMillion: Double,
    val todayCases: Long,
    val todayDeaths: Long,
    @PrimaryKey
    val updated: Long
):Serializable