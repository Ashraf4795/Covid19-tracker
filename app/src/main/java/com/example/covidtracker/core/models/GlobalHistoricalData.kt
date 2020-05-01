package com.example.covidtracker.core.models

import com.google.gson.annotations.SerializedName

data class GlobalHistoricalData(

    @SerializedName("cases")
    val casesMap:Map<String,Long>,

    @SerializedName("deaths")
    val deathsMap:Map<String,Long>,

    @SerializedName("recovered")
    val recoveredMap:Map<String,Long>
)