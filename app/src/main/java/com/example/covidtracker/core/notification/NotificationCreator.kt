package com.example.covidtracker.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.covidtracker.R
import java.net.URI

class NotificationCreator {


    fun createNotification(context: Context,title:String,message:String,intent: Intent,channelId:String): Notification{

        val pendingIntent = PendingIntent.getActivity(context,2,intent,0)

        val notification = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_background) // 3
            setContentTitle(title) // 4
            setContentText(message) // 5
            setChannelId(channelId)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            priority = NotificationCompat.PRIORITY_DEFAULT // 7

        }.build()
        return notification
    }


    fun createNotificationChannelId(context: Context, importance: Int, showBadge: Boolean, name: String, description: String): String {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // 3
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            return channelId
        }
        return ""
    }

}