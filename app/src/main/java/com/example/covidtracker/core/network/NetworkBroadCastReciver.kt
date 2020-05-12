package com.example.covidtracker.core.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log


class NetworkBroadCastReciver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        if (isNetworkConnected(context)) {
            Log.d("connectivity", "is Connected")
        } else {
            Log.d("connectivity", "is NOT **** Connected")
        }
    }

    private fun isNetworkConnected(context: Context?): Boolean {
        val connectivityManager: ConnectivityManager? = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager?
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                if (networkInfo != null) {
                    return networkInfo.isConnected
                            && networkInfo.type == ConnectivityManager.TYPE_WIFI
                            || networkInfo.type == ConnectivityManager.TYPE_MOBILE
                }
            } else {
                val network: Network? = connectivityManager.activeNetwork
                if (network != null) {
                    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                    return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
                }
            }
        }
        return false
    }
}