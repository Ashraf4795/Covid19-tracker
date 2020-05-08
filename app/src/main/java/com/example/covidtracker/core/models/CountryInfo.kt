package com.example.covidtracker.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "countryInfo")
data class CountryInfo(
    var _id: Int?,
    var flag: String?,
    var iso2: String?,
    var iso3: String?,
    var lat: Double?,
    var long: Double?
):Serializable
{
    constructor():this(0,"","","",0.0,0.0)
}