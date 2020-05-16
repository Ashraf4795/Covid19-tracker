package com.example.covidtracker.core.workManager.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.covidtracker.core.Repository
import com.example.covidtracker.core.UPDATE_WORKER_ID
import com.example.covidtracker.core.local.DatabaseBuilder
import com.example.covidtracker.core.local.LocalDataBase
import com.example.covidtracker.core.network.retrofit.RetrofitApiHelper
import com.example.covidtracker.core.network.retrofit.RetrofitBuilder
import com.example.covidtracker.core.notification.NotificationCreator
import com.example.covidtracker.countries.CountryFragment
import com.example.covidtracker.utils.Helper
import java.util.concurrent.TimeUnit

class UpdateWorker (val context: Context,workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {


    val repository = Repository(
        RetrofitApiHelper(RetrofitBuilder.apiService),
        LocalDataBase(DatabaseBuilder.getInstance(context)))


    companion object {
        fun run (interval:Long,timeUnit:TimeUnit,context: Context){

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(false)
                .build()

            val updateDataRequest = PeriodicWorkRequestBuilder<UpdateWorker>(interval,timeUnit)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(UPDATE_WORKER_ID,ExistingPeriodicWorkPolicy.REPLACE
                    ,updateDataRequest)

        }
    }

    override suspend fun doWork(): Result {
        //fetch network data code
        val data = repository.refreshData()
        //check subscribed countries
        //if there is a change notifiy user with chnages
        val subscribedCountries = repository.getSubscripedCountries()
        Log.d("subscribe",subscribedCountries.toString())
        val notifiedCountries = Helper.compareFetchedDataWithLocalData(data.second,subscribedCountries)
        val notificationCreator = NotificationCreator(context)
        notifiedCountries.forEach{
            it.countryInfo._id?.let { it1 -> notificationCreator.makeNotification(it.country, it1) }
        }
        repository.replaceOldSubscribedCountriesWithUpdatedCountries(notifiedCountries)
        return Result.success()
    }


}