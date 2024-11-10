package ru.sweetmilk.gymtracker

import android.app.Application
import ru.sweetmilk.gymtracker.di.AppComponent
import ru.sweetmilk.gymtracker.di.DaggerAppComponent

open class GymApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }
}