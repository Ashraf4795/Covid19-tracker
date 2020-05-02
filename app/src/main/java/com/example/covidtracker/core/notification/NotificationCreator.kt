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


class NotificationCreator {
    private val REQUEST_CODE = 123
    private val FLAG = 0

    fun createNotification(context: Context,title:String,message:String,intent: Intent,channelId:String,autoCancel:Boolean = true ): Notification{

        val pendingIntent = PendingIntent.getActivity(context,REQUEST_CODE,intent,FLAG)

        val notification = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_background)
            setContentTitle(title)
            setContentText(message)
            setChannelId(channelId)
            setContentIntent(pendingIntent)
            setAutoCancel(autoCancel)

            priority = NotificationCompat.PRIORITY_DEFAULT

        }.build()
        return notification
    }


    fun createNotificationChannelId(context: Context, importance: Int, showBadge: Boolean, name: String, description: String): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
            return channelId
        }
        return ""
    }


    fun playNotificationRingtone(context:Context) {
        try {
            val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(context,notificationUri)
            ringtone.play()
        }catch (exception:Exception){
            exception.printStackTrace()
        }
    }

}