package com.example.covidtracker.di.application

import android.app.Application
import com.example.covidtracker.di.component.ApplicationComponent
import com.example.covidtracker.di.component.DaggerApplicationComponent

class BaseApplication: Application() {

    val appComponent : ApplicationComponent by lazy{
        DaggerApplicationComponent.create()
    }
}