package com.example.covidtracker.core.local.shardPreference

import android.content.Context
import android.content.SharedPreferences
import com.example.covidtracker.core.INTERVAL_KEY
import com.example.covidtracker.core.workManager.worker.UpdateWorker
import java.util.concurrent.TimeUnit

class SharedPreferenceBuilder(val context: Context, val PREFE_NAME:String){
    //UpdateWorker and sharedPreference run together
    var sharedPref:SharedPreferences
    init {
        sharedPref = context.getSharedPreferences(PREFE_NAME, Context.MODE_PRIVATE)
    }


    // save interval data
   fun saveIntervalAndUpdateWorkerRequest(KEY_NAME: String, value: Long,timeUnit: TimeUnit) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putLong(KEY_NAME, value)
        editor.apply()

        // update workManger interval
        UpdateWorker.run(value,timeUnit,context)
    }


    //get saved interval
    fun getIntervalValue (KEY_NAME: String) = sharedPref.getLong(KEY_NAME, 1)



}