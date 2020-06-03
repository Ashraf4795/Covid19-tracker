package com.example.covidtracker.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.covidtracker.R
import com.example.covidtracker.core.CHANNEL_ID
import com.example.covidtracker.core.FLAG
import com.example.covidtracker.core.REQUEST_CODE
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.countries.CountryFragment
import java.net.URI


class NotificationCreator (val context: Context){


    private fun createNotification(title:String,message:String,intent: Intent): Notification{

        val pendingIntent = PendingIntent.getActivity(context,REQUEST_CODE,intent,FLAG)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_covid_icon)
            setContentTitle(title)
            setContentText(message)
            setChannelId(CHANNEL_ID)
            setContentIntent(pendingIntent)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            priority = NotificationCompat.PRIORITY_DEFAULT // 7
        }.build()
        return notification
        //run on a single thread and run serialy


    }


    private fun createNotificationChannelId(importance: Int, showBadge: Boolean, name: String, _description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = _description
            }
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }


    private fun playNotificationRingtone() {
        try {
            val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(context,notificationUri)
            ringtone.play()
        }catch (exception:Exception){
            exception.printStackTrace()
        }
    }

    fun makeNotification(@NonNull countryName:String,_id:Int) {
        val intent = Intent(context, CountryFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        createNotificationChannelId(NotificationManager.IMPORTANCE_DEFAULT,true,"Covid-19","Update about Covid-19")
        val notification = createNotification(
            "Update",
            "${countryName} has update, check it out",
            intent
        )


        with(NotificationManagerCompat.from(context)){
            _id?.let {
                notify(it,notification)
                playNotificationRingtone()
            }
        }
    }

}