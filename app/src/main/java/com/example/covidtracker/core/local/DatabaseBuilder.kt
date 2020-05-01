package com.example.covidtracker.core.local

import android.content.Context
import androidx.room.Room
import com.example.covidtracker.core.DATABASE_NAME

object DatabaseBuilder {
    private var INSTANCE: AppDataBase? = null

    fun getInstance(context: Context): AppDataBase {
        if (INSTANCE == null) {
            synchronized(AppDataBase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            DATABASE_NAME
        ).build()

}