package com.example.covidtracker.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.covidtracker.core.models.CountryData
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.core.models.SubscripEntity


@Database(entities = [GlobalData::class, CountryData::class,SubscripEntity::class],version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun  getDao():Dao
}