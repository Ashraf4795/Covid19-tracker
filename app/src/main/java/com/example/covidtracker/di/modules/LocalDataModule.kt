package com.example.covidtracker.di.modules

import android.content.Context
import androidx.room.Room
import com.example.covidtracker.core.DATABASE_NAME
import com.example.covidtracker.core.local.AppDataBase
import dagger.Module
import dagger.Provides

@Module
class LocalDataModule {

    @Provides
    fun proviedDataBaseInstance(context: Context):AppDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            DATABASE_NAME
        ).build()
}