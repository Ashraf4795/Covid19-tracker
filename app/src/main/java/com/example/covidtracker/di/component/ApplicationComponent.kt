package com.example.covidtracker.di.component

import android.content.Context
import com.example.covidtracker.di.modules.NetworkModule
import com.example.covidtracker.global.GlobalFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):ApplicationComponent
    }
    fun inject(fragment:GlobalFragment)

}