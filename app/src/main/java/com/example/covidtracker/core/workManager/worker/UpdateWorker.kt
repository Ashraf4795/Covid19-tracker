package com.example.covidtracker.core.workManager.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.covidtracker.core.Repository
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import java.util.concurrent.TimeUnit

class UpdateWorker (context: Context,workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {


    val repository = Repository(
        RetrofitApiHelper(RetrofitBuilder.apiService),
        LocalDataBase(DatabaseBuilder.getInstance(context)))


    companion object {
        fun run (interval:Long,timeUnit:TimeUnit,context: Context){
            //TODO: make constraints dynmic as param input
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED).build()

            val updateDataRequest = PeriodicWorkRequestBuilder<UpdateWorker>(interval,timeUnit)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork("updateWorker",ExistingPeriodicWorkPolicy.KEEP
                    ,updateDataRequest)

        }
    }

    override suspend fun doWork(): Result {
        //fetch network data code
        repository.refreshData()
        return Result.success()
    }


}